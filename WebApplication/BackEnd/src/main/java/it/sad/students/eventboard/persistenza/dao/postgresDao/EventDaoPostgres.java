package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.IdBroker;
import it.sad.students.eventboard.persistenza.dao.EventDao;
import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.EventType;
import it.sad.students.eventboard.persistenza.model.EventsStats;

import java.sql.*;
import java.time.LocalDate;
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
        String query ="select * from event order by event.date";
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
    public List<Event> findByType(EventType type) {
        ArrayList<Event> events = new ArrayList<>();
        String query ="select * from event where event_type=? order by event.date";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,type.toString());
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
    public List<Event> findByOrganizer(Long id) {
        ArrayList<Event> events = new ArrayList<>();
        String query ="select * from event where organizer=? order by event.date";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Event event = readEvent(rs);
                if(event!=null)
                    events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return events;
    }

    @Override
    public List<Event> findByKeywords(String keywords) {
        ArrayList<Event> events = new ArrayList<>();
        String query ="select * from event where title ilike ? order by event.date";
        try {
            keywords= "%"+keywords+"%";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,keywords);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Event event = readEvent(rs);
                if(event!=null)
                    events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return events;
    }


    @Override
    public Boolean saveOrUpdate(Event event) {
        String insertEvent = "INSERT INTO event VALUES (?, ?, ?,?, ?,?,?,?,?,?,?)";
        String updateEvent = "UPDATE event set position=?, date=?,title=?, event_type = ?, price =?,poster=?,soldout=?, description=?,organizer=?,urlticket=? where id = ?";
        PreparedStatement st = null;

        try {
            if (event.getId() == null){
                st = conn.prepareStatement(insertEvent);
                Long newId = IdBroker.getNewEventID(conn);
                event.setId(newId);
                st.setLong(1, event.getId());
                st.setLong(2,event.getPosition());
                st.setTimestamp(3, Timestamp.valueOf(event.getDate()));
                st.setString(4,event.getEventType().toString());
                st.setObject(5,event.getPrice());           // double o null
                st.setString(6,event.getUrlPoster());
                st.setObject(7,event.getSoldOut());         // boolean o null
                st.setString(8,event.getDescription());
                st.setLong(9,event.getOrganizer());
                st.setString(10,event.getTitle());
                st.setObject(11,event.getUrlTicket());      // string o null

            }else {
                st = conn.prepareStatement(updateEvent);
                st.setLong(1,event.getPosition());
                st.setTimestamp(2, Timestamp.valueOf(event.getDate()));
                st.setString(3,event.getTitle());
                st.setString(4,event.getEventType().toString());
                st.setObject(5,event.getPrice());           // double o null
                st.setString(6,event.getUrlPoster());
                st.setObject(7,event.getSoldOut());         // boolean o null
                st.setString(8,event.getDescription());
                st.setLong(9,event.getOrganizer());
                st.setObject(10,event.getUrlTicket());      // string o null
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
            DBManager.getInstance().getPositionDao().deleteById(event.getPosition());

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

    @Override
    public List<Event> findBySomeData(LocalDate initialDate, LocalDate finalDate, String region, String city) {
        ArrayList<Event> events = new ArrayList<>();

        String query=searchQueryBuilding(initialDate,finalDate,region,city);
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            Integer x=1;

            if(initialDate!=null && finalDate!=null) {
                stmt.setDate(x++, Date.valueOf(initialDate));
                stmt.setDate(x++,Date.valueOf(finalDate));
            }
            if(region!=null){
                stmt.setString(x++,region);
                if(city!=null)
                    stmt.setString(x, city);

            }

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Event event = readEvent(rs);
                if(event!=null)
                    events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return events;
    }



    private String searchQueryBuilding(LocalDate initialDate, LocalDate finalDate, String region, String city){
        String selectAndFrom ="select * from event as e";
        String where =" where ";
        if (initialDate!=null && finalDate!=null)                   //controllo le date
            where= where + "(DATE(e.date) between ? and ?)";

        if (region!=null || city!=null){
            selectAndFrom = selectAndFrom+",position as p";

            if (initialDate!=null && finalDate!=null)
                where =where +" and ";

            where=where+"e.position=p.id and ";
            if (region!=null){
                where=where+"p.region =?";
                if(city!=null )
                    where = where + " and "+"p.city=?";
            }

        }
        return selectAndFrom+where+" order by e.date";
    }

    @Override
    public List<EventsStats> topFiveRating() {
        ArrayList<EventsStats> events = new ArrayList<>();
        String query ="SELECT e.id ,e.title ,e.poster , AVG(r.rating) as media_rating " +
                        "FROM event as e LEFT JOIN review as r ON e.id = r.event " +
                        "GROUP BY e.id, e.title,e.poster " +
                        "HAVING AVG(r.rating) IS NOT NULL " +
                        "ORDER BY media_rating DESC " +
                        "LIMIT 5 ";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                EventsStats event=new EventsStats();
                event.setId(rs.getLong("id"));
                event.setTitle(rs.getString("title"));
                event.setUrlImage(rs.getString("poster"));
                Double n = rs.getDouble("media_rating");
                event.setValue(String.format("%.2f", n));
                if(event!=null)
                    events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public List<EventsStats> topFiveLikes() {
        ArrayList<EventsStats> events = new ArrayList<>();
        String query ="SELECT e.id ,e.title ,e.poster , count(p.date)  as num_likes " +
                "FROM event as e JOIN mipiace as p ON e.id = p.event " +
                "GROUP BY e.id, e.title,e.poster " +
                "ORDER BY num_likes DESC " +
                "LIMIT 5 ";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                EventsStats event=new EventsStats();
                event.setId(rs.getLong("id"));
                event.setTitle(rs.getString("title"));
                event.setUrlImage(rs.getString("poster"));
                event.setValue(rs.getInt("num_likes"));
                if(event!=null)
                    events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }


    private Event readEvent(ResultSet rs){
        try{
            Event event = new Event();
            event.setId(rs.getLong("id"));
            event.setTitle(rs.getString("title"));
            event.setPosition(rs.getLong("position"));
            event.setDate(rs.getTimestamp("date").toLocalDateTime());
            event.setEventType(EventType.valueOf(rs.getString("event_type")));
            event.setPrice(rs.getDouble("price"));
            event.setUrlPoster(rs.getString("poster"));
            event.setSoldOut(rs.getBoolean("soldout"));
            event.setDescription(rs.getString("description"));
            event.setOrganizer(rs.getLong("organizer"));
            event.setUrlTicket(rs.getString("urlticket"));

            return event;
        }catch (SQLException e){e.printStackTrace();}

        return null;
    }
}
