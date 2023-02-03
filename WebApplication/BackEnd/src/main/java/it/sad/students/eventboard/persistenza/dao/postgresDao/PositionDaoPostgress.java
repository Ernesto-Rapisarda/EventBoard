package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.IdBroker;
import it.sad.students.eventboard.persistenza.dao.PositionDao;
import it.sad.students.eventboard.persistenza.model.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            return null;
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(Position position) {
        String insertPosition = "INSERT INTO position VALUES (?,?,?,?,?,?)";
        String updatePosition = "UPDATE position set region=?,city=?, address=?, longitude=?,latitude=? where id = ?";

        PreparedStatement st=null;
        try {
            if (position.getId() == null){
                st = conn.prepareStatement(insertPosition);
                position.setId(IdBroker.getNewPositionID(conn));
                st.setLong(1,position.getId());
                st.setString(2,position.getRegion());
                st.setString(3,position.getCity());
                st.setObject(4,position.getAddress());      //string o null
                st.setObject(5,position.getLongitude());    //double o null
                st.setObject(6,position.getLatitude());     //double o null
            }
            else{
                st = conn.prepareStatement(updatePosition);
                st.setString(1,position.getRegion());
                st.setString(2,position.getCity());
                st.setObject(3,position.getAddress());      //string o null
                st.setObject(4,position.getLongitude());    //double o null
                st.setObject(5,position.getLatitude());     //double o null
                st.setLong(6,position.getId());


            }
            st.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void delete(Position position) {
        String query = "DELETE FROM position WHERE id = ?";

        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, position.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM position WHERE id = ?";

        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Position readPosition(ResultSet rs){
        try{
            Position position=new Position();
            position.setId(rs.getLong("id"));
            position.setRegion(rs.getString("region"));
            position.setCity(rs.getString("city"));
            position.setAddress(rs.getString("address"));
            position.setLongitude(rs.getDouble("longitude"));
            position.setLatitude(rs.getDouble("latitude"));
            return position;
        }catch (SQLException e){e.printStackTrace();}

        return null;
    }
}
