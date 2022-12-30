package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.Event;

import java.util.List;

public interface EventDao {
    List<Event> findAll();

    Event findByPrimaryKey(Long id);

    void saveOrUpdate(Event event);

    void delete(Event event);
}
