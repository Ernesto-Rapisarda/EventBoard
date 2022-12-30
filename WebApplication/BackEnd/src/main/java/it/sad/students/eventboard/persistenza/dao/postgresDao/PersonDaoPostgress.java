package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.dao.PersonDao;
import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersonDaoPostgress implements PersonDao {
    Connection conn;
    public PersonDaoPostgress(Connection conn) {
        this.conn = conn;
    }
    @Override
    public List<Person> findAll() {
        ;
    }

    @Override
    public Person findByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public void saveOrUpdate(Person person) {

    }

    @Override
    public void delete(Person person) {

    }

    private Event setEvent (ResultSet rs){
        try{
            Event event = new Event();
            event.setId(rs.getLong("id"));
            event.setPosition(rs.getLong("name"));
            event.setDate(rs.getDate("lastname"));
            event.setEventType(rs.getLong("username"));
            event.setPrice(rs.getDouble("email"));
            event.setUrlPoster(rs.getString("active_status"));
            event.setSoldOut(rs.getBoolean("soldout"));
            event.setDescription(rs.getString("description"));
            event.setPublisher(rs.getLong("publisher"));
            return event;
        }catch (SQLException e){e.printStackTrace();}

        return null;
    }
}
