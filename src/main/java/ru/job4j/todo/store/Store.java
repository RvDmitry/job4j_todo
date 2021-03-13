package ru.job4j.todo.store;

import ru.job4j.todo.models.Item;
import ru.job4j.todo.models.User;

import java.util.List;

/**
 * Interface Store
 * Интерфейс описывает методы взаимодействия с хранилищем данных.
 * @author Dmitry Razumov
 * @version 1
 */
public interface Store extends AutoCloseable {
    /**
     * Метод сохраняет задание в хранилище данных.
     * @param item Задание.
     * @return Сохраненное задание.
     */
    Item save(Item item);

    /**
     * Метод сохраняет пользователя в хранилище данных.
     * @param user Пользователь.
     * @return Сохраненный пользователь.
     */
    User save(User user);

    /**
     * Метод обновляет задание в хранилище.
     * @param item Задание, которое нужно обновить.
     * @return true, если задание обновлено успешно, иначе false.
     */
    boolean update(Item item);

    /**
     * Метод возвращает задание из хранилища по его идентификатору.
     * @param id Идентификатор задания.
     * @return Задание.
     */
    Item findItemById(int id);

    /**
     * Метод возвращает задание из хранилища по его идентификатору для заданного пользователя.
     * @param id Идентификатор задания.
     * @param user Пользователь, задание для которого нужно найти.
     * @return Задание.
     */
    Item findItemByIdForUser(int id, User user);

    /**
     * Метод возвращает пользователя из хранилища по его email.
     * @param email Email пользователя.
     * @return Пользователь.
     */
    User findUserByEmail(String email);

    /**
     * Метод возвращает список всех заданий из хранилища.
     * @return Список заданий.
     */
    List<Item> findAllItems();

    /**
     * Метод возвращает список всех заданий из хранилища для заданного пользователя.
     * @param user Пользователь, задания для которого нужно найти.
     * @return Список заданий.
     */
    List<Item> findAllItemsForUser(User user);

    /**
     * Метод возвращает список заданий, которые в зависимости
     * от переданного параметра, будут выполнены либо нет.
     * @param bool Параметр задающий условие, выполнено ли задание.
     * @return Список заданий.
     */
    List<Item> findItemsIsDone(boolean bool);

    /**
     * Метод возвращает список заданий для заданного пользователя,
     * которые в зависимости от переданного параметра, будут выполнены либо нет.
     * @param bool Параметр задающий условие, выполнено ли задание.
     * @param user Пользователь, задания для которого нужно найти.
     * @return Список заданий.
     */
    List<Item> findItemsIsDoneForUser(boolean bool, User user);

    /**
     * Метод возвращает количество заданий у заданного пользователя.
     * @param user Пользователь.
     * @return Количество заданий.
     */
    int numberForUser(User user);
}
