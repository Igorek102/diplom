package ru.igorek.core.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Игорек
 */
@Entity
public class Event implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long eventId;
    private Date dat;
    private String message;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historyId", nullable = false)
    private History history;

    public Event() {
    }

    public Date getDat() {
        return dat;
    }

    public void setDat(Date dat) {
        this.dat = dat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        if (this.eventId != other.eventId) {
            return false;
        }
        return true;
    }
}
