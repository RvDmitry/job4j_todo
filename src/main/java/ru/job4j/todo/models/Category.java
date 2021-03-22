package ru.job4j.todo.models;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class Category
 * Класс характеризует категорию.
 * @author Dmitry Razumov
 * @version 1
 */
@Entity
@Table(name = "categories")
public class Category {
    /**
     * Идентификатор категории.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Наименование категории.
     */
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Фабрика создает категорию.
     * @param name Наименование.
     * @param user Пользователь, для которого создается категория.
     * @return Категория.
     */
    public static Category of(String name, User user) {
        Category category = new Category();
        category.name = name;
        category.user = user;
        return category;
    }

    /**
     * Метод возвращает идентификатор.
     * @return Идентификатор.
     */
    public int getId() {
        return id;
    }

    /**
     * Метод задает идентификатор.
     * @param id Идентификатор.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Метод возвращает наименование категории.
     * @return Наименование.
     */
    public String getName() {
        return name;
    }

    /**
     * Метод задает наименование категории.
     * @param name Наименование.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Метод возвращает пользователя категории.
     * @return Пользователь.
     */
    public User getUser() {
        return user;
    }

    /**
     * Метод задает пользователя категории.
     * @param user Пользователь.
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        return id == category.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Category{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", user=" + user
                + '}';
    }
}
