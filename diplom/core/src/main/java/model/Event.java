package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

/**
 *
 * @author Игорек
 */
@Entity
public class Event implements Serializable,IEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long eventId;
    private Status status;
    private Date date;
    private String description;

    public Event() {
    }

    public long getId() {
        return eventId;
    }

    public void setId(long id) {
        this.eventId = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
