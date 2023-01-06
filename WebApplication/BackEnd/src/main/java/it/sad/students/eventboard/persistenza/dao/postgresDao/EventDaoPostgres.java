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
    public List<Event> findByType(Long type) {
        ArrayList<Event> events = new ArrayList<>();
        String query ="select * from event where event_type=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1,type);
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
    public Boolean saveOrUpdate(Event event) {
        String insertEvent = "INSERT INTO event VALUES (?, ?, ?,?, ?,?,?,?,?,?)";
        String updateEvent = "UPDATE event set position=?, date=?,title=?, event_type = ?, price =?,poster=?,soldout=?, description=?,publisher=? where id = ?";
        PreparedStatement st = null;

        try {
            if (event.getId() == null){
                st = conn.prepareStatement(insertEvent);
                Long newId = IdBroker.getNewEventID(conn);
                event.setId(newId);
                st.setLong(1, event.getId());
                st.setLong(2,event.getPosition());
                st.setDate(3, java.sql.Date.valueOf(event.getDate()));
                st.setLong(4,event.getEventType());
                st.setDouble(5,event.getPrice());
                st.setString(6,event.getUrlPoster());
                st.setBoolean(7,event.getSoldOut());
                st.setString(8,event.getDescription());
                st.setLong(9,event.getPublisher());
                st.setString(10,event.getTitle());

            }else {
                st = conn.prepareStatement(updateEvent);
                st.setLong(1,event.getPosition());
                st.setDate(2, java.sql.Date.valueOf(event.getDate()));
                st.setString(3,event.getTitle());
                st.setLong(4,event.getEventType());
                st.setDouble(5,event.getPrice());
                st.setString(6,event.getUrlPoster());
                st.setBoolean(7,event.getSoldOut());
                st.setString(8,event.getDescription());
                st.setLong(9,event.getPublisher());
                st.setLong(10,event.getId());
            }

            st.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
            event.setTitle(rs.getString("title"));
            event.setPosition(rs.getLong("position"));
            event.setDate(rs.getDate("date").toLocalDate());
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
