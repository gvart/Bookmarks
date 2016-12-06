package dev.gva.bookmarks.service.ServiceImpl;

import dev.gva.bookmarks.DAO.EventDAO;
import dev.gva.bookmarks.model.Event;
import dev.gva.bookmarks.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pika on 27.11.2016.
 */
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDAO eventDAO;

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    @Transactional
    public void addEvent(Event event) {
        eventDAO.addEvent(event);
    }

    @Override
    @Transactional
    public void updateEvent(Event event) {
        eventDAO.updateEvent(event);
    }

    @Override
    @Transactional
    public void deleteEvent(int id) {
        eventDAO.deleteEvent(id);
    }

    @Override
    @Transactional
    public Event findEventById(int id) {
        return null;
    }

    @Override
    @Transactional
    public List<Event> listEvents() {
        return eventDAO.listEvents();
    }
}
