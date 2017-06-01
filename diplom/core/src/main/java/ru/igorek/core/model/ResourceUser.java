package ru.igorek.core.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Игорек
 */
@Entity
public class ResourceUser implements Serializable{
    @Id
    private String login;
    @Column(nullable = false)
    private String password;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "resource_url", nullable = false)
    private Resource resource;

    public ResourceUser() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.login);
        return hash;
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
        final ResourceUser other = (ResourceUser) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        return true;
    }
}
