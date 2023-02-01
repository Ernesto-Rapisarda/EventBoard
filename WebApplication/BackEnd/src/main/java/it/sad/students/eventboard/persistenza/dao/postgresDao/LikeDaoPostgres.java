package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.dao.LikeDao;
import it.sad.students.eventboard.persistenza.model.Like;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikeDaoPostgres implements LikeDao {

    Connection conn;

    public LikeDaoPostgres(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Like> findAll() {
        ArrayList<Like> likes = new ArrayList<>();
        String query ="select * from mipiace";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Like like = readEvent(rs);
                if(like!=null)
                    likes.add(like);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likes;
    }


    @Override
    public List<Like> findByPerson(Long person) {
        ArrayList<Like> likes = new ArrayList<>();
        String query ="select * from mipiace where person= ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1,person);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Like like = readEvent(rs);
                if(like!=null)
                    likes.add(like);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likes;
    }

    @Override
    public List<Like> findByEvent(Long id) {
        ArrayList<Like> likes = new ArrayList<>();
        String query ="select * from mipiace where event= ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Like like = readEvent(rs);
                if(like!=null)
                    likes.add(like);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likes;
    }


    @Override
    public Like findByPrimaryKey(Long person, Long event) {
        String query ="select * from mipiace where person=? and event=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, person);
            stmt.setLong(2, event);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Like like = readEvent(rs);
                if(like!=null)
                    return like;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveOrUpdate(Like like) {
        String insertLike = "INSERT INTO mipiace VALUES (?, ?, ?)";
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(insertLike);
            st.setLong(1, like.getPerson());
            st.setLong(2, like.getIdEvent());
            st.setDate(3, java.sql.Date.valueOf(like.getDate()));

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Like like) {
        String query = "DELETE FROM mipiace WHERE person = ? and event =?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, like.getPerson());
            st.setLong(2,like.getIdEvent());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean deleteByEvent(Long idEvent) throws SQLException{
        String query = "DELETE FROM mipiace WHERE event = ?";
        PreparedStatement st = conn.prepareStatement(query);
        st.setLong(1, idEvent);
        st.executeUpdate();
        return true;
    }

    private Like readEvent(ResultSet rs) {
        try{
            Like like = new Like();
            like.setPerson(rs.getLong("person"));
            like.setIdEvent(rs.getLong("event"));
            like.setDate(rs.getDate("date").toLocalDate());
            return like;
        }catch (SQLException e){e.printStackTrace();}

        return null;
    }
}
