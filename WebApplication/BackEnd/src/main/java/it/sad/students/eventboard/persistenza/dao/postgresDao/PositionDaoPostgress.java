package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.IdBroker;
import it.sad.students.eventboard.persistenza.dao.PositionDao;
import it.sad.students.eventboard.persistenza.model.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionDaoPostgress implements PositionDao {
    Connection conn;
    public PositionDaoPostgress(Connection conn) {
        this.conn = conn;
    }
    @Override
    public List<Position> findAll() {
        ArrayList<Position> positions = new ArrayList<>();
        String query ="select * from position";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Position position = readPosition(rs);
                if(position!=null)
                    positions.add(position);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }

    @Override
    public Position findByPrimaryKey(Long id) {
        String query = "select * from position where id=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Position position = readPosition(rs);
                if(position!=null)
                    return position;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;    }

    @Override
    public void saveOrUpdate(Position position) {
        String insertPosition = "INSERT INTO position VALUES (?)";
        //String updatePosition = "UPDATE position set name=? where id = ?";

        PreparedStatement st=null;
        try {
            position.setId(IdBroker.getNewPositionID(conn));
            st = conn.prepareStatement(insertPosition);

            st.setLong(1, position.getId());


            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*
        try {

            if (position.getId() == null) {

                st = conn.prepareStatement(insertEvent);
                Long newId = IdBroker.getNewPersonID(conn);
                position.setId(newId);
                st.setLong(1, position.getId());
                st.setString(2, position.getName());
                st.executeUpdate();

            //}else {

                st = conn.prepareStatement(updateStr);
                st.setString(1, position.getName());
                st.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        */

    }

    @Override
    public void delete(Position position) {
        String query = "DELETE FROM position WHERE id = ?";
        /*
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, position.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    private Position readPosition(ResultSet rs){
        //try{
            Position position=new Position();
            //position.setId(rs.getLong("id"));
            //position.setName(rs.getString("name"));
            return position;
        //}catch (SQLException e){e.printStackTrace();}

        //return null;
    }
}
