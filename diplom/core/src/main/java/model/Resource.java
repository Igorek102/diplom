package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author Игорек
 */
@Entity
public class Resource implements Serializable, IEntity{
    @Id
    private String URL;
    @OneToMany(mappedBy = "resource", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Application> applications = new HashSet<>();
    
    @ElementCollection
    @MapKeyColumn(name = "login")
    @Column(name = "password", nullable = false)
    private Map<String, String> users = new HashMap<>();

    public Resource() {
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }
}
