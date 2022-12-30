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
                Event event = setEvent(rs);
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
                Event event = setEvent(rs);
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
        if (event.getId() == null) {
            String insertEvent = "INSERT INTO event VALUES (?, ?, ?, ?,?,?,?,?,?)";

            PreparedStatement st;
            try {
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

                st.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }/*else {
            String updateStr = "UPDATE ristorante set nome = ?, descrizione = ?, cap_ubicazione = ? where id = ?";

            PreparedStatement st;
            try {
                st = conn.prepareStatement(updateStr);

                st.setString(1, ristorante.getNome());
                st.setString(2, ristorante.getDescrizione());
                st.setString(3, ristorante.getUbicazione());

                st.setLong(4, ristorante.getId());



                st.executeUpdate();

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }*/

    }

    @Override
    public void delete(Event event) {

    }



    private Event setEvent (ResultSet rs){
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
