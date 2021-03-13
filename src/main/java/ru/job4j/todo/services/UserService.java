package ru.job4j.todo.services;

import ru.job4j.todo.models.User;
import ru.job4j.todo.store.Store;

/**
 * Class UserService
 * Сервис класс, реализует бизнес-логику приложения. Выполняет операции с объектом User.
 * @author Dmitry Razumov
 * @version 1
 */
public class UserService {
    /**
     * Ссылка на объект реализующий операции с данными на уровне БД.
     */
    private final Store store;

    /**
     * Конструктор инициализирует объект-сервис.
     * @param store DAO класс.
     */
    public UserService(Store store) {
        this.store = store;
    }

    /**
     * Метод сохраняет пользователя.
     * @param user Пользователь.
     * @return Сохраненный пользователь.
     */
    public User saveUser(User user) {
        return store.save(user);
    }

    /**
     * Метод находит пользователя по его email.
     * @param email Email пользователя.
     * @return Пользователь.
     */
    public User findUser(String email) {
        return store.findUserByEmail(email);
    }
}
