package ru.igorek.core.utils;

import java.io.File;
import model.Application;
import model.Event;
import model.History;
import model.Parameter;
import model.Resource;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Игорек
 */
public class HibernateUtil {
        private static final String DEFAULT_HIBERNATE_CONFIG_PATH = "src/main/resources/hibernate/hibernate.cfg.xml";
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
                    .addAnnotatedClass(Application.class)
                    .addAnnotatedClass(Event.class)
                    .addAnnotatedClass(History.class)
                    .addAnnotatedClass(Parameter.class)
                    .addAnnotatedClass(Resource.class);
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
