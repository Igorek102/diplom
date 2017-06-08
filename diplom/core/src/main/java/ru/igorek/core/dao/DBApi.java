package ru.igorek.core.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ru.igorek.core.model.*;
import ru.igorek.core.utils.HibernateUtil;

/**
 *
 * @author Игорек
 */
public class DBApi {
    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    
    public void addResource(String url){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Resource resource = new Resource();
        resource.setURL(url);
        session.persist(resource);
        transaction.commit();
        session.close();
    }
    
    public List<Resource> getAllResources(){
        Session session = sessionFactory.openSession();
        List<Resource> list = session.createCriteria(Resource.class).list();
        session.close();
        return list;
    }
    
    public List<String> getAllUrls(){
        Session session = sessionFactory.openSession();
        List<Resource> resources = session.createCriteria(Resource.class).list();
        session.close();
        List<String> urls = new ArrayList<>();
        resources.stream().forEach((resource) -> urls.add(resource.getURL()));
        return urls;
    }
    
    public void deleteResource(String url){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(session.get(Resource.class, url));
        transaction.commit();
        session.close();
    }
    
    public void addUserToResource(String resourceUrl, String login, String password){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Resource resource = session.get(Resource.class, resourceUrl);
        ResourceUser user = new ResourceUser();
        user.setLogin(login);
        user.setPassword(password);
        user.setResource(resource);
        resource.getUsers().add(user);
        transaction.commit();
        session.close();
    }
    
    public void deleteUser(String resourceUrl, String login){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ResourceUser resourceUser = new ResourceUser();
        resourceUser.setLogin(login);
        session.get(Resource.class, resourceUrl).getUsers().remove(resourceUser);
        transaction.commit();
        session.close();
    }
    
    public Set<ResourceUser> getUsersByResource(String resourceUrl){
        Session session = sessionFactory.openSession();
        Set<ResourceUser> users = new HashSet<>();
        users.addAll(session.get(Resource.class, resourceUrl).getUsers());
        session.close();
        return users;
    }
    
    public void addApplicationToResource(String resourceUrl, String applicationName, String applicationPath){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Resource resource = session.get(Resource.class, resourceUrl);
        History history = new History();
        Application application = new Application();
        application.setName(applicationName);
        application.setPath(applicationPath);
        history.setApplication(application);
        application.setHistory(history);
        application.setResource(resource);
        resource.getApplications().add(application);
        transaction.commit();
        session.close();
    }
    
    public void updateApplication(Application application){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(application);
        transaction.commit();
        session.close();
    }
    
    public void deleteApplication(long applicationID){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(session.get(Application.class, applicationID));
        transaction.commit();
        session.close();
    }
    
    public List<Application> getApplicationsByResource(String resourceUrl){
        Session session = sessionFactory.openSession();
        List<Application> applications = new ArrayList<>();
        applications.addAll(session.get(Resource.class, resourceUrl).getApplications());
        session.close();
        return applications;
    }
    
    public void addParameterToApplication(long applicationId, String parameterName){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Application application = session.get(Application.class, applicationId);
        Parameter parameter = new Parameter();
        parameter.setParameterName(parameterName);
        parameter.setApplication(application);
        application.getParameters().add(parameter);
        transaction.commit();
        session.close();
    }
    
    public void deleteParameter(long parameterId){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(session.get(Parameter.class, parameterId));
        transaction.commit();
        session.close();
    }
    
    public List<Parameter> getParametersByApplication(long applicationId){
        Session session = sessionFactory.openSession();
        List<Parameter> parameters = new ArrayList<>();
        parameters.addAll(session.get(Application.class, applicationId).getParameters());
        session.close();
        return parameters;
    }
    
    public void addParameterValue(long parameterId, String valueName, String valueDescription){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.get(Parameter.class, parameterId).getValues().put(valueName, valueDescription);
        transaction.commit();
        session.close();
    }
    
    public void deleteParameterValue(long parameterId, String parameterName){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Parameter parameter = session.get(Parameter.class, parameterId);
        parameter.getValues().remove(parameterName);
        transaction.commit();
        session.close();
    }
    
    public Map<String,String> getParameterValues(long parameterId){
        Session session = sessionFactory.openSession();
        Map<String,String> values = new HashMap<>();
        values.putAll(session.get(Parameter.class, parameterId).getValues());
        session.close();
        return values;
    }
    
    public void clearApplicationHistory(long applicationId){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.get(Application.class, applicationId).getHistory().getEvents().clear();
        transaction.commit();
        session.close();
    }
    
    //Здесь нужно будет добавить параметры для имента
    public void addEventToHistory(long applicationId, Date date, Status status){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        History history = session.get(Application.class, applicationId).getHistory();
        Event event = new Event();
        event.setHistory(history);
        event.setDate(date);
        event.setStatus(status);
        history.getEvents().add(event);
        transaction.commit();
        session.close();
    }
    
    public List<Event> getApplicationHistory(long applicationId){
        Session session = sessionFactory.openSession();
        List<Event> events = new ArrayList<>();
        events.addAll(session.get(Application.class, applicationId).getHistory().getEvents());
        session.close();
        return events;
    }
    
    public boolean checkLoginAndPassword(String resourceUrl, String login, String password) throws NoSuchUserException{
        boolean res = false;
        Session session = sessionFactory.openSession();
        Resource resource = new Resource();
        resource.setURL(resourceUrl);
        ResourceUser resourceUser = new ResourceUser();
        resourceUser.setResource(resource);
        resourceUser.setLogin(login);
        Optional<ResourceUser> user = Optional.ofNullable(session.get(ResourceUser.class, resourceUser));
        session.close();
        if (user.isPresent())
            res = user.get().getPassword().equals(password);
        else throw new NoSuchUserException();
        return res;
    }
    
    public void closeSessionFactory(){
        sessionFactory.close();
    }
}
