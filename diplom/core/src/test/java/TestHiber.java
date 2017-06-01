
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import model.Application;
import model.History;
import model.Parameter;
import model.Resource;
import org.hibernate.Session;
import org.junit.Test;
import ru.igorek.core.api.DBApi;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.igorek.core.utils.HibernateUtil;

/**
 *
 * @author Игорек
 */
public class TestHiber {
    private static DBApi dBApi = new DBApi();
    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    
    @Test
    public void createSchema(){}
    
    @Test
    public void addUser(){
        Session session =sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Resource resource = (Resource)session.get(Resource.class, "127.127.127.128");
        resource.getUsers().put("qwe", "parol");
        session.persist(resource);
        transaction.commit();
        session.close();
        System.out.println(resource.getURL());
    }
    
    @Test
    public void addResource(){
        Resource resource = new Resource();
        resource.setURL("127.127.127.128");
        dBApi.addEntity(resource);
    }
    
    @Test
    public void getUsersByResource(){
        
    }
    
    @Test
    public void deleteResource(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Resource resource = new Resource();
        resource.setURL("127.127.127.127");
        session.remove(resource);
        transaction.commit();
        session.close();
    }
    
    @Test
    public void addApplication(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Resource resource = (Resource)session.get(Resource.class, "127.127.127.128");
        Application application = new Application();
        application.setResource(resource);
        resource.getApplications().add(application);
        transaction.commit();
        session.close();
    }
    
    @Test
    public void getApplicationsByResource(){
        Session session = sessionFactory.openSession();
        Resource resource = session.get(Resource.class, "127.127.127.128");
        Set<Application> applications = new HashSet<>();
        applications.addAll(resource.getApplications());
        session.close();
        applications.stream().forEach((application) -> System.out.println(application.getId()));
    }
    
    @Test
    public void getResourceByApplication(){
        Session session = sessionFactory.openSession();
        Resource resource = session.get(Application.class, 2l).getResource();
        session.close();
        System.out.println(resource.getURL());
    }
    
    @Test
    public void addParameter(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Parameter parameter = new Parameter();
        parameter.setParameterName("parameter1");
        session.get(Application.class, 1l).getParameters().add(parameter);
        transaction.commit();
        session.close();
    }
    
    @Test
    public void addParameterValue(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.get(Parameter.class, 2l).getValues().put("n", "name");
        transaction.commit();
        session.close();
    }
    
    @Test
    public void getParameterValues(){
        Session session = sessionFactory.openSession();
        Map<String,String> values = new HashMap<>();
        values.putAll(session.get(Parameter.class, 2l).getValues());
        session.close();
        System.out.println(values);
    }
    
    @Test
    public void addHistoryToApplication(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Application application = session.get(Application.class, 1l);
        History history = new History();
        history.setApplication(application);
        application.setHistory(history);
        transaction.commit();
        session.close();
    }
    
    @Test
    public void getHistoryByApplication(){
        Session session = sessionFactory.openSession();
        History history = session.get(Application.class, 1l).getHistory();
        session.close();
        System.out.println(history.getId());
    }
}
