package dev.gva.bookmarks.DAO.DAOImpl;

import dev.gva.bookmarks.DAO.EventTypeDAO;
import dev.gva.bookmarks.model.EventType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by pika on 11/11/16.
 */

public class EventTypeDAOImpl implements EventTypeDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public void addEventType(EventType eventType) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(eventType);
        logger.info("EventType saved successfully, EventType details=" + eventType);
    }

    @Override
    public void updateEventType(EventType eventType) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(eventType);
        logger.info("EventType updated successfully, EventType details=" + eventType);
    }

    @Override
    public void deleteEventType(EventType eventType) {
        Session session = this.sessionFactory.getCurrentSession();
        session.delete(eventType);
        logger.info("EventType deleted successfully, EventType details=" + eventType);
    }

    @Override
    public List<EventType> listEventTypes() {
        Session session = this.sessionFactory.getCurrentSession();
        List<EventType> eventTypes = session.createQuery("from EventType").list();
        for (EventType et : eventTypes) {
            logger.info("EventTypes List::" + et);
        }
        return eventTypes;
    }

    @Override
    public List<EventType> listOrderedEventTypes(String order) {
        Session session = this.sessionFactory.getCurrentSession();
        List<EventType> eventTypes = session.createQuery("from EventType et order by et.name " + order).list();
        for (EventType et : eventTypes) {
            logger.info("EventTypes List::" + et);
        }
        return eventTypes;
    }

    @Override
    public EventType findEventTypeByName(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        EventType et = (EventType) session.createQuery("from EventType where name=:name").setParameter("name", name).uniqueResult();
        logger.info("Event type loaded successfully, EventType details=" + et);

        return et;
    }

    @Override
    public EventType findEventTypeById(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        EventType et = (EventType) session.load(EventType.class, new Integer(id));
        logger.info("Event type loaded successfully, EventType details=" + et);
        return et;
    }
}
