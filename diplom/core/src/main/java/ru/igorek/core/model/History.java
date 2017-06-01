package ru.igorek.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Игорек
 */
@Entity
public class History implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long historyId;
    
    @OneToOne
    @JoinColumn(name = "applicationId", unique = true)
    private Application application;
    
    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    public History() {
    }

    public long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(long historyId) {
        this.historyId = historyId;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
