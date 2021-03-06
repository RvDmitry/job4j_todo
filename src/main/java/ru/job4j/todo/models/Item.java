package ru.job4j.todo.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Class Item
 * Класс характеризует задание.
 * @author Dmitry Razumov
 * @version 1
 */
@Entity
@Table(name = "items")
public class Item {
    /**
     * Идентификатор задания.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Описание задания.
     */
    private String description;
    /**
     * Дата и время создания задания.
     */
    private Timestamp created;
    /**
     * Готовность задания.
     */
    private boolean done;

    /**
     * Конструктор инициализирует объект.
     */
    public Item() { }

    /**
     * Конструктор инициализирует задания.
     * @param description Описание задания.
     */
    public Item(String description) {
        this.description = description;
        long droppedMillis = 1000 * (System.currentTimeMillis() / 1000);
        created = new Timestamp(droppedMillis);
    }

    /**
     * Метод возвращает идентификатор задания.
     * @return Идентификатор.
     */
    public int getId() {
        return id;
    }

    /**
     * Метод задает идентификатор задания.
     * @param id Идентификатор.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Метод возвращает описание задания.
     * @return Описание.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Метод задает описание задания.
     * @param description Описание.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Метод возвращает дату создания задания.
     * @return Дата создания.
     */
    public Timestamp getCreated() {
        return created;
    }

    /**
     * Метод задает дату создания задания.
     * @param created Дата создания.
     */
    public void setCreated(Timestamp created) {
        this.created = created;
    }

    /**
     * Метод проверяет готовность задания.
     * @return true, если дело выполнено, иначе false.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Метод устанавливает готовность задания.
     * @param done true, если задание выполнено, иначе false.
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", description='" + description + '\''
                + ", created=" + created
                + ", done=" + done
                + '}';
    }
}
