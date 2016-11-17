package dev.gva.bookmarks.DAO;


import dev.gva.bookmarks.model.EventType;

import java.util.List;
import java.util.Set;

/**
 * Created by pika on 11/11/16.
 */
public interface EventTypeDAO {
    public void addEventType(EventType eventType);
    public void updateEventType (EventType eventType);
    public void deleteEventType(EventType eventType);
    public EventType findEventTypeById(Integer id);
    public List<EventType> listEventTypes();
}
