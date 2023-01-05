package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.EventType;

import java.util.List;

public interface EventTypeDao {
    List<EventType> findAll();

    EventType findByPrimaryKey(Long id);

    void saveOrUpdate(EventType eventType);

    void delete(EventType eventType);
}
