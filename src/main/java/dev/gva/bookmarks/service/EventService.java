package dev.gva.bookmarks.service;

import dev.gva.bookmarks.model.Event;
import dev.gva.bookmarks.model.User;

import java.util.List;

/**
 * Created by pika on 27.11.2016.
 */
public interface EventService {
    void addEvent(Event event);
    void updateEvent(Event event);
    void deleteEvent(int id);
    Event findEventById(int id);
    List<Event> listEvents();
}
