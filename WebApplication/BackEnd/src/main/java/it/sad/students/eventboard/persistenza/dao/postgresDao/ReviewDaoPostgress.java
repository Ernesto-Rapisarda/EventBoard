package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.dao.ReviewDao;
import it.sad.students.eventboard.persistenza.model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDaoPostgress implements ReviewDao {
    Connection conn;
    public ReviewDaoPostgress(Connection conn) {
        this.conn = conn;
    }
    @Override
    public List<Review> findAll() {
        ArrayList<Review> reviews = new ArrayList<>();
        String query ="select * from review";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Review review = readReview(rs);
                if(review!=null)
                    reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    @Override
    public List<Review> findByPerson(Long person) {
        ArrayList<Review> reviews = new ArrayList<>();
        String query ="select * from review where person=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1,person);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Review review = readReview(rs);
                if(review!=null)
                    reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    @Override
    public Review findByPrimaryKey(Long person,Long event) {
        String query = "select * from review where person = ? and event=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, person);
            stmt.setLong(2,event);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Review review = readReview(rs);
                if(review!=null)
                    return review;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(Review review) {
        String insertEvent = "INSERT INTO review VALUES (?,?,?,?,?)";
        String updateStr = "UPDATE review set date=?,message=?,rating=? where person = ? and event=?";

        PreparedStatement st=null;
        try {
            Review revieDb= findByPrimaryKey(review.getPerson(), review.getEvent());
            if (revieDb == null) {

                st = conn.prepareStatement(insertEvent);
                st.setLong(1, review.getPerson());
                st.setLong(2, review.getEvent());
                st.setTimestamp(3, Timestamp.valueOf(review.getDate()));
                st.setString(4,review.getMessage());
                st.setInt(5,review.getRating());
                st.executeUpdate();

            }else {

                st = conn.prepareStatement(updateStr);
                st.setTimestamp(1, Timestamp.valueOf(review.getDate()));
                st.setString(2,review.getMessage());
                st.setInt(3,review.getRating());

                st.setLong(4, review.getPerson());
                st.setLong(5, review.getEvent());
                st.executeUpdate();


            }

            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void delete(Review review) {
        String query = "DELETE FROM review WHERE person = ? and event=?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, review.getPerson());
            st.setLong(2,review.getEvent());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteByEvent(Long idEvent) throws SQLException{
        String query = "DELETE FROM review WHERE event = ?";
        PreparedStatement st = conn.prepareStatement(query);
        st.setLong(1, idEvent);
        st.executeUpdate();
        return true;
    }

    @Override
    public List<Review> findByEvent(Long id) {
        ArrayList<Review> reviews = new ArrayList<>();
        String query ="select * from review where event=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Review review = readReview(rs);
                if(review!=null)
                    reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }


    private Review readReview(ResultSet rs){
        try{
            Review review=new Review();
            review.setPerson(rs.getLong("person"));
            review.setEvent(rs.getLong("event"));
            review.setDate(rs.getTimestamp("date").toLocalDateTime());
            review.setMessage(rs.getString("message"));
            review.setRating(rs.getInt("rating"));
            return review;
        }catch (SQLException e){e.printStackTrace();}

        return null;
    }
}
