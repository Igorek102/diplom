package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ConstraintMode;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author Игорек
 */
@Entity
public class Application implements Serializable, IEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long applicationId;
    
    @ManyToOne
    @JoinColumn(name = "resource_url", nullable = false)
    private Resource resource;
    
    private String name;
    private String description;
    private String path;
    
    @OneToOne(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private History history;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "application_parameters", joinColumns = @JoinColumn(name = "applicationId"), inverseJoinColumns = @JoinColumn(name = "parameterId"))
    private Set<Parameter> parameters = new HashSet<>();

    public Application() {
    }

    public long getId() {
        return applicationId;
    }

    public void setId(long id) {
        this.applicationId = id;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public Set<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(Set<Parameter> parameters) {
        this.parameters = parameters;
    }
}
