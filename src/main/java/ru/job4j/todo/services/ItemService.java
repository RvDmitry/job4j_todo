package ru.job4j.todo.services;

import ru.job4j.todo.models.Item;
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
     * Конструтор инициализирует объект-сервис.
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
     * @return Дело.
     */
    public Item findItem(int id) {
        return store.findById(id);
    }

    /**
     * Метод возвращает список всех заданий.
     * @return Список заданий.
     */
    public List<Item> findItems() {
        return store.findAll();
    }

    /**
     * Метод возвращает список выполненных либо невыполненных заданий.
     * @param bool Параметр, определяющий статус заданий.
     * @return Список заданий.
     */
    public List<Item> findItemsIsDone(boolean bool) {
        return store.findIsDone(bool);
    }
}
