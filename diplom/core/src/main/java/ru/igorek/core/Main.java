package ru.igorek.core;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.log4j.Logger;
import ru.igorek.core.dao.DBApi;
import ru.igorek.core.dao.SshApi;
import ru.igorek.core.utils.ConfigurationUtil;
import ru.igorek.core.utils.HibernateUtil;

/**
 *
 * @author Игорек
 */
public class Main{
    private static Logger log = Logger.getLogger(Main.class);
    
    public static void main(String[] args) throws IOException, URISyntaxException{
        /*DBApi dbApi = new DBApi();
        dbApi.addResource("1255");
        dbApi.closeSessionFactory();*/
        File file = new File("/etc/asd.txt");
        System.out.println(file.getAbsolutePath());
    }
}
