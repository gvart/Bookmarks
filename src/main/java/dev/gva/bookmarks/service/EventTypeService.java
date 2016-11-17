package dev.gva.bookmarks.service;

import dev.gva.bookmarks.model.EventType;

import java.util.List;

/**
 * Created by pika on 11/11/16.
 */
public interface EventTypeService {
    public void addEventType(EventType eventType);
    public void updateEventType (EventType eventType);
    public void deleteEventType(EventType eventType);
    public EventType findEventTypeById(Integer id);
    public List<EventType> listEventTypes();
}
