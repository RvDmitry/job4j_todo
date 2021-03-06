package ru.job4j.todo.store;

import ru.job4j.todo.models.Item;

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
    Item findById(int id);

    /**
     * Метод возвращает список всех заданий из хранилища.
     * @return Список заданий.
     */
    List<Item> findAll();

    /**
     * Метод возвращает список заданий, которые в зависимости
     * от переданного параметра, будут выполнены либо нет.
     * @param bool Параметр задающий условие, выполнено ли задание.
     * @return Список заданий.
     */
    List<Item> findIsDone(boolean bool);
}
