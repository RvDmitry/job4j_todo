package ru.job4j.todo.services;

import ru.job4j.todo.models.Category;
import ru.job4j.todo.models.User;
import ru.job4j.todo.store.Store;

import java.util.List;

/**
 * Class CategoryService
 * Сервис класс, реализует бизнес-логику приложения. Выполняет операции с объектом Category.
 * @author Dmitry Razumov
 * @version 1
 */
public class CategoryService {
    /**
     * Ссылка на объект реализующий операции с данными на уровне БД.
     */
    private final Store store;

    /**
     * Конструктор инициализирует объект-сервис.
     * @param store DAO класс.
     */
    public CategoryService(Store store) {
        this.store = store;
    }

    /**
     * Метод сохраняет категорию.
     * @param category Категория.
     * @return Сохраненная категория.
     */
    public Category saveCategory(Category category) {
        return store.save(category);
    }

    /**
     * Метод находит категорию по ее идентификатору.
     * @param id Идентификатор категории.
     * @return Категория.
     */
    public Category findCategory(int id) {
        return store.findCategoryById(id);
    }

    /**
     * Метод возвращает список всех категорий.
     * @return Список категорий.
     */
    public List<Category> findCategories() {
        return store.findAllCategories();
    }

    /**
     * Метод возвращает список всех категорий для заданного пользователя.
     * @param user Пользователь, категории которого нужно найти.
     * @return Список категорий.
     */
    public List<Category> findCategoriesForUser(User user) {
        return store.findAllCategoriesForUser(user);
    }
}
