package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.IdBroker;
import it.sad.students.eventboard.persistenza.dao.CommentDao;
import it.sad.students.eventboard.persistenza.model.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoPostgres implements CommentDao {
    Connection conn;

    public CommentDaoPostgres(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Comment> findAll() {
        ArrayList<Comment> eventsComment = new ArrayList<>();
        String query ="select * from comment";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Comment comment = readEvent(rs);
                if(comment!=null)
                    eventsComment.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventsComment;
    }

    @Override
    public List<Comment> findByPerson(Long person) {
        ArrayList<Comment> eventsComment = new ArrayList<>();
        String query ="select * from comment where person=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1,person);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Comment comment = readEvent(rs);
                if(comment!=null)
                    eventsComment.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventsComment;
    }

    @Override
    public List<Comment> findByEvent(Long id) {
        ArrayList<Comment> eventsComment = new ArrayList<>();
        String query ="select * from comment where event=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Comment comment = readEvent(rs);
                if(comment!=null)
                    eventsComment.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventsComment;
    }


    @Override
    public Comment findByPrimaryKey(Long id) {
        String query ="select * from comment where id=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Comment comment = readEvent(rs);
                if(comment!=null)
                    return comment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(Comment comment) {
        String insertComment = "INSERT INTO comment VALUES (?, ?, ?,?,?)";
        String updateComment = "UPDATE comment set date=?, message=?, person=?, event=? where id = ?";
        PreparedStatement st = null;

        try {
            if (comment.getId() == null){
                st = conn.prepareStatement(insertComment);
                Long newId = IdBroker.getNewCommentID(conn);
                comment.setId(newId);

                st.setLong(1,comment.getId());
                st.setTimestamp(2, Timestamp.valueOf(comment.getDate()));
                st.setString(3,comment.getMessage());
                st.setLong(4,comment.getPerson());
                st.setLong(5,comment.getEvent());

            }
            else{
                st = conn.prepareStatement(updateComment);
                st.setTimestamp(1, Timestamp.valueOf(comment.getDate()));
                st.setString(2,comment.getMessage());
                st.setLong(3,comment.getPerson());
                st.setLong(4,comment.getEvent());
                st.setLong(5,comment.getId());

            }

            st.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void delete(Comment comment) {
        String query = "DELETE FROM comment WHERE id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, comment.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean deleteByEvent(Long idEvent) throws SQLException{

        String query = "DELETE FROM comment WHERE event = ?";

        PreparedStatement st = conn.prepareStatement(query);
        st.setLong(1, idEvent);
        st.executeUpdate();
        return true;
    }

    private Comment readEvent(ResultSet rs) {
        try{
            Comment comment = new Comment();
            comment.setId(rs.getLong("id"));
            comment.setDate(rs.getTimestamp("date").toLocalDateTime());
            comment.setMessage(rs.getString("message"));
            comment.setPerson(rs.getLong("person"));
            comment.setEvent(rs.getLong("event"));
            return comment;
        }catch (SQLException e){e.printStackTrace();}

        return null;
    }
}
