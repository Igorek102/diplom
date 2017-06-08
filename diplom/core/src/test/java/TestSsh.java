import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import ru.igorek.core.dao.SshApi;

/**
 *
 * @author Игорек
 */
public class TestSsh {
    private static SshApi sshApi = new SshApi();
    
    private static final int SSH_PORT = 22;
    private static final int CONNECTION_TIMEOUT = 10000;
    
    private static final String HOSTNAME = "localhost";
    private static final String USERNAME = "asd";
    private static final String PASSWORD = "asd";
    
    
    @Test 
    public void getData(){
        List<String> info = sshApi.startApplication(HOSTNAME, SSH_PORT, USERNAME, PASSWORD, "java -jar d:\\bkparse\\target\\bkparse-1.0-SNAPSHOT.jar");
        info.stream().forEach((string) -> System.out.println(string));
    }
    
    @Test
    public void getSes() {
        try{
            JSch jSch = new JSch();
            Session session = jSch.getSession("127.0.0.1");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(CONNECTION_TIMEOUT);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void getOSName(){
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("os.version"));
        System.out.println(System.getProperty("user.name"));
    }
    
    @Test
    public void tst() throws IOException{
        System.out.println(sshApi.getDataFromLocalHost());
    }
}
