package ru.igorek.core.api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.igorek.core.utils.HibernateUtil;
import ru.igorek.core.entities.IEntity;

/**
 *
 * @author Игорек
 */
public class DBApi {
    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    
    public void addEntity(IEntity entity){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
    }
}
