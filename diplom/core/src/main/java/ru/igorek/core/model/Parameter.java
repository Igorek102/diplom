package ru.igorek.core.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;

/**
 *
 * @author Игорек
 */
@Entity
public class Parameter implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long parameterId;
    @Column(nullable = false)
    private String parameterName;
    private String parameterDescription;
    
    @ElementCollection
    @MapKeyColumn(name = "valueName")
    @Column(name = "valueDescription")
    private Map<String, String> values = new HashMap<>();
    
    @ManyToOne
    @JoinColumn(name = "applicationId", nullable = false)
    private Application application;

    public Parameter() {
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterDescription() {
        return parameterDescription;
    }

    public void setParameterDescription(String parameterDescription) {
        this.parameterDescription = parameterDescription;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    public long getParameterId() {
        return parameterId;
    }

    public void setParameterId(long parameterId) {
        this.parameterId = parameterId;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
