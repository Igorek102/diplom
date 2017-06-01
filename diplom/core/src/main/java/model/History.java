package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author Игорек
 */
@Entity
public class History implements Serializable, IEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long historyId;
    
    @OneToOne
    @JoinColumn(name = "applicationId")
    private Application application;
    
    @OneToMany(mappedBy = "id",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Event> events = new HashSet<>();

    public History() {
    }

    public long getId() {
        return historyId;
    }

    public void setId(long id) {
        this.historyId = id;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
