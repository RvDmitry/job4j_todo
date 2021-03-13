package ru.job4j.todo.services;

import ru.job4j.todo.models.Item;
import ru.job4j.todo.models.User;
import ru.job4j.todo.store.Store;

import java.util.List;

/**
 * Class ItemService
 * Сервис класс, реализует бизнес-логику приложения. Выполняет операции с объектом Item.
 * @author Dmitry Razumov
 * @version 1
 */
public class ItemService {
    /**
     * Ссылка на объект реализующий операции с данными на уровне БД.
     */
    private final Store store;

    /**
     * Конструктор инициализирует объект-сервис.
     * @param store DAO класс.
     */
    public ItemService(Store store) {
        this.store = store;
    }

    /**
     * Метод сохраняет задание.
     * @param item Задание.
     * @return Сохраненное задание.
     */
    public Item saveItem(Item item) {
        return store.save(item);
    }

    /**
     * Метод обновляет задание.
     * @param item Задание.
     * @return true, если задание обновлено успешно, иначе false.
     */
    public boolean updateItem(Item item) {
        return store.update(item);
    }

    /**
     * Метод находит задание по его идентификатору.
     * @param id Идентификатор задания.
     * @return Задание.
     */
    public Item findItem(int id) {
        return store.findItemById(id);
    }

    /**
     * Метод находит задание по его идентификатору для заданного пользователя.
     * @param id Идентификатор задания.
     * @param user Пользователь, задание которого нужно найти.
     * @return Задание.
     */
    public Item findItemForUser(int id, User user) {
        return store.findItemByIdForUser(id, user);
    }

    /**
     * Метод возвращает список всех заданий.
     * @return Список заданий.
     */
    public List<Item> findItems() {
        return store.findAllItems();
    }

    /**
     * Метод возвращает список всех заданий для заданного пользователя.
     * @param user Пользователь, задания которого нужно найти.
     * @return Список заданий.
     */
    public List<Item> findItemsForUser(User user) {
        return store.findAllItemsForUser(user);
    }

    /**
     * Метод возвращает список выполненных либо невыполненных заданий.
     * @param bool Параметр, определяющий статус заданий.
     * @return Список заданий.
     */
    public List<Item> findItemsIsDone(boolean bool) {
        return store.findItemsIsDone(bool);
    }

    /**
     * Метод возвращает список выполненных либо невыполненных заданий для заданного пользователя.
     * @param bool Параметр, определяющий статус заданий.
     * @param user Пользователь, задания которого нужно найти.
     * @return Список заданий.
     */
    public List<Item> findItemsIsDoneForUser(boolean bool, User user) {
        return store.findItemsIsDoneForUser(bool, user);
    }

    /**
     * Метод возвращает количество заданий у заданного пользователя.
     * @param user Пользователь.
     * @return Количество заданий.
     */
    public int number(User user) {
        return store.numberForUser(user);
    }
}
