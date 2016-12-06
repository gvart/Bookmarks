package dev.gva.bookmarks.DAO.DAOImpl;

import dev.gva.bookmarks.DAO.EventDAO;
import dev.gva.bookmarks.model.Event;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pika on 27.11.2016.
 */
@Repository
public class EventDAOImpl implements EventDAO {

    private static final Logger logger = LoggerFactory.getLogger(EventDAOImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public void addEvent(Event event) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(event);
        logger.info("Event saved successfully, EventType details=" + event);
    }

    @Override
    public void updateEvent(Event event) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(event);
        logger.info("Event updated successfully, Event details=" + event);
    }

    @Override
    public void deleteEvent(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Event e = (Event) session.load(Event.class, new Integer(id));
        if (null != e) {
            session.delete(e);
        }
        logger.info("Event deleted successfully, Event details=" + e);
    }

    @Override
    public Event findEventById(int id) {
        return null;
    }

    @Override
    public List<Event> listEvents() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Event> eventsList = session.createQuery("from Event").list();
        for (Event e : eventsList) {
            logger.info("Events List::" + e);
        }
        return eventsList;
    }
}
