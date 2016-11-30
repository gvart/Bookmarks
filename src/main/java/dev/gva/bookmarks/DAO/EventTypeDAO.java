package dev.gva.bookmarks.DAO;


import dev.gva.bookmarks.model.EventType;

import java.util.List;
import java.util.Set;

/**
 * Created by pika on 11/11/16.
 */
public interface EventTypeDAO {

    void addEventType(EventType eventType);
    void updateEventType (EventType eventType);
    void deleteEventType(EventType eventType);
    EventType findEventTypeById(Integer id);
    List<EventType> listEventTypes();
    EventType findEventTypeByName(String name);

}
