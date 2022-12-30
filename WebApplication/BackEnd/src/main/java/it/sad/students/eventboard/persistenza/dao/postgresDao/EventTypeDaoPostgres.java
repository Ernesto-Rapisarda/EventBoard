package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.IdBroker;
import it.sad.students.eventboard.persistenza.dao.EventTypeDao;
import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.EventType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventTypeDaoPostgres implements EventTypeDao {
    Connection conn;

    public EventTypeDaoPostgres(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<EventType> findAll() {
        ArrayList<EventType> eventsType = new ArrayList<>();
        String query ="select * from event_type";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                EventType eventType = readEvent(rs);
                if(eventType!=null)
                    eventsType.add(eventType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventsType;
    }



    @Override
    public EventType findByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public void saveOrUpdate(EventType eventType) {
        if (eventType.getId() == null) {
            String insertEvent = "INSERT INTO event_type VALUES (?, ?, ?)";

            PreparedStatement st;
            try {
                st = conn.prepareStatement(insertEvent);

                Long newId = IdBroker.getNewEventTypeID(conn);
                eventType.setId(newId);

                st.setLong(1, eventType.getId());
                st.setString(2,eventType.getName());
                st.setString(3, eventType.getDescription());


                st.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void delete(EventType eventType) {

    }

    private EventType readEvent(ResultSet rs) {
        try{
            EventType eventType = new EventType();
            eventType.setId(rs.getLong("id"));
            eventType.setName(rs.getString("name"));
            eventType.setDescription(rs.getString("description"));

            return eventType;
        }catch (SQLException e){e.printStackTrace();}

        return null;
    }
}
