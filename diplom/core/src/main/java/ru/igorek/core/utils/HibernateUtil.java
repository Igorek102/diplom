package ru.igorek.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.igorek.core.model.Application;
import ru.igorek.core.model.Event;
import ru.igorek.core.model.History;
import ru.igorek.core.model.Parameter;
import ru.igorek.core.model.Resource;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.igorek.core.model.ResourceUser;

/**
 *
 * @author Игорек
 */
public class HibernateUtil {
        private static final String DEFAULT_HIBERNATE_CONFIG_PATH = ConfigurationUtil.getConfigurationEntry("DEFAULT_HIBERNATE_CONFIG_PATH");
        private static ServiceRegistry serviceRegistry;
        private static String pathToFile;
        private static SessionFactory sessionFactory;

        public HibernateUtil(String path) {
            pathToFile = path;
        }
        
        
        /**
        * Создание фабрики
        * @throws HibernateException
        */
        private static void configureSessionFactory() {
            try {
                if (pathToFile == null)
                    pathToFile = DEFAULT_HIBERNATE_CONFIG_PATH;
                File file = getFile(pathToFile);
                Configuration configuration = new Configuration().configure(file);
                serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
                MetadataSources sources = new MetadataSources(serviceRegistry)
                        .addAnnotatedClass(Application.class)
                        .addAnnotatedClass(Event.class)
                        .addAnnotatedClass(History.class)
                        .addAnnotatedClass(Parameter.class)
                        .addAnnotatedClass(Resource.class)
                        .addAnnotatedClass(ResourceUser.class);
                sessionFactory = sources.buildMetadata().buildSessionFactory();
            } catch (IOException ex) {
                Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /**
        * Получить фабрику сессий
        * @return {@link SessionFactory}
        */
        public static SessionFactory getSessionFactory(){
            if (sessionFactory == null)
                configureSessionFactory();
            return sessionFactory;
        }
        
        private static File getFile(String path) throws IOException{
            InputStream in = HibernateUtil.class.getResourceAsStream(path);
            byte[] buffer = new byte[in.available()];
            in.read(buffer);

            File targetFile = new File("temp_file.tmp");
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
            return targetFile;
        }
        public static void closeSessionFactory(){
            if (sessionFactory != null)
                sessionFactory.close();
        }
}
