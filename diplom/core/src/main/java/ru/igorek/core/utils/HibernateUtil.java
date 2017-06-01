package ru.igorek.core.utils;

import java.io.File;
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
            this.pathToFile = path;
        }
        
        
        /**
        * Создание фабрики
        * @throws HibernateException
        */
        private static void configureSessionFactory() throws HibernateException {
            if (pathToFile == null)
                pathToFile = DEFAULT_HIBERNATE_CONFIG_PATH;
            File file = new File(pathToFile);
            Configuration configuration = new Configuration().configure(file);
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            MetadataSources sources = new MetadataSources(serviceRegistry)
                    .addResource(ConfigurationUtil.getConfigurationEntry("HIBERNATE_NAMED_QUERIES"))
                    .addAnnotatedClass(Application.class)
                    .addAnnotatedClass(Event.class)
                    .addAnnotatedClass(History.class)
                    .addAnnotatedClass(Parameter.class)
                    .addAnnotatedClass(Resource.class)
                    .addAnnotatedClass(ResourceUser.class);
            sessionFactory = sources.buildMetadata().buildSessionFactory();
        }
        /**
        * Получить фабрику сессий
        * @return {@link SessionFactory}
        */
        public static SessionFactory getSessionFactory() {
            if (sessionFactory == null)
                configureSessionFactory();
            return sessionFactory;
        }
}
