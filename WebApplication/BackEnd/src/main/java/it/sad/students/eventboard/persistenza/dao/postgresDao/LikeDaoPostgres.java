package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.IdBroker;
import it.sad.students.eventboard.persistenza.dao.LikeDao;
import it.sad.students.eventboard.persistenza.model.Comment;
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
        String query ="select * from like";
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
    public Like findByPrimaryKey(Long person, Long event) {
        String query ="select * from like where persone=? and event=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            stmt.setLong(1, person);
            stmt.setLong(2, event);

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
        String insertLike = "INSERT INTO like VALUES (?, ?, ?)";
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(insertLike);
            st.setLong(1, like.getPerson());
            st.setLong(2, like.getIdEvent());
            st.setDate(3, (Date) like.getDate());

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Like like) {
        String query = "DELETE FROM like WHERE persone = ? and event =?";
        try {
            // TODO: 30/12/2022 rimozione a cascata
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, like.getPerson());
            st.setLong(2,like.getIdEvent());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Like readEvent(ResultSet rs) {
        try{
            Like like = new Like();
            like.setPerson(rs.getLong("persone"));
            like.setIdEvent(rs.getLong("event"));
            like.setDate(rs.getDate("date"));
            return like;
        }catch (SQLException e){e.printStackTrace();}

        return null;
    }
}
