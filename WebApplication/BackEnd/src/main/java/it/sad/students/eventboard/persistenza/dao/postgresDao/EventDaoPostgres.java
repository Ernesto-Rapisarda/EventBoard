package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.IdBroker;
import it.sad.students.eventboard.persistenza.dao.EventDao;
import it.sad.students.eventboard.persistenza.model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDaoPostgres implements EventDao {
    Connection conn;

    public EventDaoPostgres(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Event> findAll() {
        ArrayList<Event> events = new ArrayList<>();
        String query ="select * from event";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Event event = readEvent(rs);
                if(event!=null)
                    events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public Event findByPrimaryKey(Long id) {
        String query = "select * from event where id=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Event event = readEvent(rs);
                if(event!=null)
                    return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveOrUpdate(Event event) {
        String insertEvent = "INSERT INTO event VALUES (?, ?, ?, ?,?,?,?,?,?)";
        String updateEvent = "UPDATE event set position=?, date=?, event_type = ?, price =?,poster=?,soldout=?, description=?,publisher=? where id = ?";
        PreparedStatement st = null;

        try {
            if (event.getId() == null){
                st = conn.prepareStatement(insertEvent);
                Long newId = IdBroker.getNewEventID(conn);
                event.setId(newId);
                st.setLong(1, event.getId());
                st.setLong(2,event.getPosition());
                st.setDate(3, (Date) event.getDate());
                st.setLong(4,event.getEventType());
                st.setDouble(5,event.getPrice());
                st.setString(6,event.getUrlPoster());
                st.setBoolean(7,event.getSoldOut());
                st.setString(8,event.getDescription());
                st.setLong(9,event.getPublisher());
            }else {
                st = conn.prepareStatement(updateEvent);
                st.setLong(1,event.getPosition());
                st.setDate(2, (Date) event.getDate());
                st.setLong(3,event.getEventType());
                st.setDouble(4,event.getPrice());
                st.setString(5,event.getUrlPoster());
                st.setBoolean(6,event.getSoldOut());
                st.setString(7,event.getDescription());
                st.setLong(8,event.getPublisher());
                st.setLong(9,event.getId());
            }

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Event event) {
        String query = "DELETE FROM event WHERE id = ?";
        try {
            // TODO: 30/12/2022 rimozione a cascata 
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, event.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private Event readEvent(ResultSet rs){
        try{
            Event event = new Event();
            event.setId(rs.getLong("id"));
            event.setPosition(rs.getLong("position"));
            event.setDate(rs.getDate("date"));
            event.setEventType(rs.getLong("event_type"));
            event.setPrice(rs.getDouble("price"));
            event.setUrlPoster(rs.getString("poster"));
            event.setSoldOut(rs.getBoolean("soldout"));
            event.setDescription(rs.getString("description"));
            event.setPublisher(rs.getLong("publisher"));
            return event;
        }catch (SQLException e){e.printStackTrace();}

        return null;
    }
}
