package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.IdBroker;
import it.sad.students.eventboard.persistenza.dao.EventDao;
import it.sad.students.eventboard.persistenza.model.Event;

import java.sql.*;
import java.time.temporal.ChronoUnit;
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
        String insertEvent = "INSERT INTO event VALUES (?, ?, ?,?, ?,?,?,?,?,?,?)";
        String updateEvent = "UPDATE event set position=?, date=?,title=?, event_type = ?, price =?,poster=?,soldout=?, description=?,publisher=?,time=? where id = ?";
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
                st.setLong(9,event.getOrganizer());
                st.setString(10,event.getTitle());
                st.setTime(11,java.sql.Time.valueOf(event.getTime().truncatedTo(ChronoUnit.MINUTES)));

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
                st.setLong(9,event.getOrganizer());
                st.setTime(10,java.sql.Time.valueOf(event.getTime().truncatedTo(ChronoUnit.MINUTES)));
                st.setLong(11,event.getId());
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
        String eventQuery = "DELETE FROM event WHERE id = ?";
        try {
            conn.setAutoCommit(false);

            DBManager.getInstance().getCommentDao().deleteByEvent(event.getId());
            DBManager.getInstance().getLikeDao().deleteByEvent(event.getId());
            DBManager.getInstance().getPartecipationDao().deleteByEvent(event.getId());
            DBManager.getInstance().getReviewDao().deleteByEvent(event.getId());


            PreparedStatement deleteEvent = conn.prepareStatement(eventQuery);
            deleteEvent.setLong(1, event.getId());
            deleteEvent.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null){
                try{
                    conn.rollback();
                }catch (SQLException exception){
                    exception.printStackTrace();
                }
            }
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
            event.setOrganizer(rs.getLong("organizer"));
            event.setTime(rs.getTime("time").toLocalTime().truncatedTo(ChronoUnit.MINUTES));
            return event;
        }catch (SQLException e){e.printStackTrace();}

        return null;
    }
}
