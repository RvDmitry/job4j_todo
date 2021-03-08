package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.todo.models.Item;

import java.util.List;
import java.util.function.Function;

/**
 * Class HbmStore
 * Класс осуществляет взаимодействие с базой данных с помощью Hibernate.
 * @author Dmitry Razumov
 * @version 1
 */
public class HbmStore implements Store {
    /**
     * Поле содержит логер для записи информации в лог-файл.
     */
    private static final Logger LOG = LoggerFactory.getLogger(HbmStore.class.getName());

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    /**
     * Метод сохраняет задание в БД.
     * @param item Задание.
     * @return Сохраненное задание.
     */
    @Override
    public Item save(Item item) {
        return this.tx(session -> {
            session.save(item);
            return item;
        });
    }

    /**
     * Метод обновляет задание в БД.
     * @param item Задание, которое нужно обновить.
     * @return true, если задание обновлено успешно, иначе false.
     */
    @Override
    public boolean update(Item item) {
        return this.tx(session -> {
            session.update(item);
            return true;
        });
    }

    /**
     * Метод возвращает задание из БД по его идентификатору.
     * @param id Идентификатор задания.
     * @return Задание.
     */
    @Override
    public Item findById(int id) {
        return this.tx(session -> session.get(Item.class, id));
    }

    /**
     * Метод возвращает список всех заданий из БД.
     * @return Список заданий.
     */
    @Override
    public List<Item> findAll() {
        return this.tx(session -> session.createQuery("from Item").list());
    }

    /**
     * Метод возвращает список заданий, которые в зависимости
     * от переданного параметра, будут выполнены либо нет.
     * @param bool Параметр задающий условие, выполнено ли задание.
     * @return Список заданий.
     */
    @Override
    public List<Item> findIsDone(boolean bool) {
        return this.tx(session -> {
            Query query = session.createQuery("from Item I where I.done = :bool");
            query.setParameter("bool", bool);
            return query.list();
        });
    }

    /**
     * Метод закрывает соединение с БД.
     * @throws Exception Исключение.
     */
    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    /**
     * Метод реализует обертку над всеми транзакциями Hibernate к базе данных.
     * @param command Запрос к базе данных, который нужно выполнить.
     * @param <T> Тип возвращаемого значения.
     * @return Результат запроса.
     */
    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
