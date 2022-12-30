package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.dao.EventDao;
import it.sad.students.eventboard.persistenza.model.Event;

import java.sql.Connection;
import java.util.List;

public class EventDaoPostgres implements EventDao {
    Connection conn;

    public EventDaoPostgres(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Event> findAll() {
        return null;
    }

    @Override
    public Event findByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public void saveOrUpdate(Event event) {

    }

    @Override
    public void delete(Event event) {

    }
}
