package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.IdBroker;
import it.sad.students.eventboard.persistenza.dao.EventTypeDao;
import it.sad.students.eventboard.persistenza.model.EventType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String query ="select * from event_type where id=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            stmt.setLong(1, id);
            if(rs.next()){
                EventType eventType = readEvent(rs);
                if(eventType!=null)
                    return eventType;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveOrUpdate(EventType eventType) {
        String insertEventType = "INSERT INTO event_type VALUES (?, ?, ?)";
        String updateEventType = "UPDATE event_type set name=?, description=? where id = ?";
        PreparedStatement st = null;

        try {
            if (eventType.getId() == null){
                st = conn.prepareStatement(insertEventType);
                Long newId = IdBroker.getNewEventTypeID(conn);
                eventType.setId(newId);

                st.setLong(1, eventType.getId());
                st.setString(2,eventType.getName());
                st.setString(3, eventType.getDescription());
            }
            else{
                st = conn.prepareStatement(updateEventType);
                st.setString(1,eventType.getName());
                st.setString(2, eventType.getDescription());
                st.setLong(3, eventType.getId());

            }

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(EventType eventType) {
        String query = "DELETE FROM event_type WHERE id = ?";
        try {
            // TODO: 30/12/2022 rimozione a cascata
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, eventType.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
