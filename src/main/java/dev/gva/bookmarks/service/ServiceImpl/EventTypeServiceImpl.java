package dev.gva.bookmarks.service.ServiceImpl;

import dev.gva.bookmarks.DAO.EventTypeDAO;
import dev.gva.bookmarks.model.EventType;
import dev.gva.bookmarks.service.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pika on 11/11/16.
 */
public class EventTypeServiceImpl implements EventTypeService {

    @Autowired
    private EventTypeDAO eventTypeDAO;

    public void setEventTypeDAO(EventTypeDAO eventTypeDAO) {
        this.eventTypeDAO = eventTypeDAO;
    }

    @Override
    @Transactional
    public void addEventType(EventType eventType) {
        this.eventTypeDAO.addEventType(eventType);
    }

    @Override
    @Transactional
    public void updateEventType(EventType eventType) {
        this.eventTypeDAO.updateEventType(eventType);
    }

    @Override
    @Transactional
    public void deleteEventType(EventType eventType) {
        this.eventTypeDAO.deleteEventType(eventType);
    }

    @Override
    @Transactional
    public List<EventType> listEventTypes() {
        return this.eventTypeDAO.listEventTypes();
    }

    @Override
    @Transactional
    public EventType findEventTypeById(Integer id) {
        return this.eventTypeDAO.findEventTypeById(id);
    }
}
