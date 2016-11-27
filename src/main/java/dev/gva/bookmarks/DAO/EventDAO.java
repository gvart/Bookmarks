package dev.gva.bookmarks.DAO;

import dev.gva.bookmarks.model.Event;
import java.util.List;

/**
 * Created by pika on 27.11.2016.
 */
public interface EventDAO {

    void addEvent(Event event);
    void updateEvent(Event event);
    void deleteEvent(int id);
    Event findEventById(int id);
    List<Event> listEvents();
}
