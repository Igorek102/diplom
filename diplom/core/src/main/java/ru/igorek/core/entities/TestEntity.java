package ru.igorek.core.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Игорек
 */
@Entity
public class TestEntity implements Serializable, IEntity{
    @Id
    private long id;

    public TestEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
