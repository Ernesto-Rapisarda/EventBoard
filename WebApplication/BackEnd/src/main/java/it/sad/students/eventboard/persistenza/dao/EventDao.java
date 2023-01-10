package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.EventType;

import java.util.List;

public interface EventDao {
    List<Event> findAll();

    Event findByPrimaryKey(Long id);

    List<Event> findByType(EventType type);
    List<Event> findByOrganizer(Long id);

    Boolean saveOrUpdate(Event event);

    void delete(Event event);
}
