import java.util.Map;
import java.util.Set;
import ru.igorek.core.model.Application;
import ru.igorek.core.model.Parameter;
import ru.igorek.core.model.Resource;
import org.junit.Test;
import ru.igorek.core.dao.DBApi;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import ru.igorek.core.dao.NoSuchUserException;
import ru.igorek.core.model.Event;
import ru.igorek.core.model.ResourceUser;
import ru.igorek.core.model.Status;
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
    public void addResource(){
        for (int i = 1; i < 20; i++) {
            dBApi.addResource("127.0.0." + i);
        }
    }
    
    @Test
    public void getAllResources(){
        List<Resource> resources = dBApi.getAllResources();
        resources.stream().forEach((resource) -> System.out.println(resource.getURL()));
    }
    
    @Test
    public void deleteResource(){
        dBApi.deleteResource("127.0.0.2");
    }
    
    @Test
    public void addUsersToResources(){
        List<Resource> resources = dBApi.getAllResources();
        resources.stream().forEach((resource) -> {
            for (int i = 0; i < 5; i++) {
                dBApi.addUserToResource(resource.getURL(), "log"+i, "pass"+i);
            }
        });
    }
    
    @Test
    public void deleteUser(){
        dBApi.deleteUser("127.0.0.2", "log1");
    }
    
    @Test
    public void getUsersByResource(){
        Set<ResourceUser> users = dBApi.getUsersByResource("127.0.0.2");
        users.stream().forEach((user) -> System.out.println(user.getLogin()));
    }
    
    @Test
    public void addApplicationToResource(){
        List<Resource> resources = dBApi.getAllResources();
        resources.stream().forEach((resource) -> {
           for (int i = 0; i < 5; i++) {
               dBApi.addApplicationToResource(resource.getURL(), "aaplication"+i, "","path"+i);
            } 
        });
    }
    
    @Test
    public void deleteApplication(){
        dBApi.deleteApplication(81);
    }
    
    @Test
    public void getApplicationsByResource(){
        List<Application> appls = dBApi.getApplicationsByResource("127.0.0.2");
        appls.stream().forEach((application) -> System.out.println(application.getName()));
    }
    
    @Test
    public void addParameter(){
        List<Resource> resources = dBApi.getAllResources();
        resources.stream().forEach((resource) -> {
            List<Application> appls = dBApi.getApplicationsByResource(resource.getURL());
            appls.stream().forEach((application) -> {
                for (int i = 0; i < 2; i++) {
                    dBApi.addParameterToApplication(application.getApplicationId(), "par"+i, "desc" + i, true);
                }
            });
        });
    }
    
    @Test
    public void getParametersByApplication(){
        List<Parameter> parameters = dBApi.getParametersByApplication(85);
        parameters.stream().forEach((parameter) -> System.out.println(parameter.getParameterId()));
    }
    
    @Test
    public void deleteParameter(){
        dBApi.deleteParameter(161);
    }
    
    @Test
    public void addParameterValue(){
        for (int i = 0; i < 3; i++) {
            dBApi.addParameterValue(161, "an"+i, "vd"+i);
        }
    }
    
    @Test
    public void deleteParameterValue(){
        dBApi.deleteParameterValue(161, "an1");
    }
    
    @Test
    public void getParameterValues(){
        Map<String,String> values = dBApi.getParameterValues(161);
        System.out.println(values);
    }
    
    @Test
    public void clearHistory(){
        dBApi.clearApplicationHistory(83);
    }
    
    @Test
    public void chechPass(){
        try {
            System.out.println(dBApi.checkLoginAndPassword("127.0.0.1", "admin", "admi"));
        } catch (NoSuchUserException ex) {
            Logger.getLogger(TestHiber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
