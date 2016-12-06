package dev.gva.bookmarks.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by pika on 11/11/16.
 */
@Entity
@Table(name = "event_types")
public class EventType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    @NotEmpty(message = "Insert EventType name")
    private String name;

    /* @ManyToMany(fetch = FetchType.LAZY ,mappedBy = "events")
     private Set<Event> events;
 */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "EventType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventType eventType = (EventType) o;

        if (id != eventType.id) return false;
        return name != null ? name.equals(eventType.name) : eventType.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
/*
    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
*/
}
