package ru.job4j.todo.store;

import org.hibernate.HibernateException;
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
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.save(item);
        tx.commit();
        session.close();
        return item;
    }

    /**
     * Метод обновляет задание в БД.
     * @param item Задание, которое нужно обновить.
     * @return true, если задание обновлено успешно, иначе false.
     */
    @Override
    public boolean update(Item item) {
        boolean rsl = true;
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(item);
            tx.commit();
        } catch (HibernateException e) {
            LOG.error("Ошибка обновления.", e);
            rsl = false;
        }
        return rsl;
    }

    /**
     * Метод возвращает задание из БД по его идентификатору.
     * @param id Идентификатор задания.
     * @return Задание.
     */
    @Override
    public Item findById(int id) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Item item = session.get(Item.class, id);
        tx.commit();
        session.close();
        return item;
    }

    /**
     * Метод возвращает список всех заданий из БД.
     * @return Список заданий.
     */
    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Item");
        List items = query.list();
        tx.commit();
        session.close();
        return items;
    }

    /**
     * Метод возвращает список заданий, которые в зависимости
     * от переданного параметра, будут выполнены либо нет.
     * @param bool Параметр задающий условие, выполнено ли задание.
     * @return Список заданий.
     */
    @Override
    public List<Item> findIsDone(boolean bool) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Item I where I.done = :bool");
        query.setParameter("bool", bool);
        List items = query.list();
        tx.commit();
        session.close();
        return items;
    }

    /**
     * Метод закрывает соединение с БД.
     * @throws Exception Исключение.
     */
    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}