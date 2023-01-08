package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.dao.PartecipationDao;
import it.sad.students.eventboard.persistenza.model.Partecipation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartecipationDaoPostgress implements PartecipationDao {
    Connection conn;
    public PartecipationDaoPostgress(Connection conn) {
        this.conn = conn;
    }
    @Override
    public List<Partecipation> findAll() {
        ArrayList<Partecipation> partecipations = new ArrayList<>();
        String query ="select * from partecipation";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Partecipation partecipation = readPartecipation(rs);
                if(partecipation!=null)
                    partecipations.add(partecipation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partecipations;    }

    @Override
    public Partecipation findByPrimaryKey(Long person,Long event) {
        String query = "select * from partecipation where person = ? and event=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, person);
            stmt.setLong(2,event);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Partecipation partecipation = readPartecipation(rs);
                if(partecipation!=null)
                    return partecipation;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;    }

    @Override
    public List<Partecipation> findByEvent(Long id) {
        ArrayList<Partecipation> partecipations = new ArrayList<>();
        String query ="select * from partecipation where event=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Partecipation partecipation = readPartecipation(rs);
                if(partecipation!=null)
                    partecipations.add(partecipation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partecipations;
    }

    @Override
    public void saveOrUpdate(Partecipation partecipation) {
        String insertEvent = "INSERT INTO partecipation VALUES (?,?,?)";
        String updateStr = "UPDATE partecipation set date=? where person = ? and event=?";

        PreparedStatement st=null;
        try {
            st = conn.prepareStatement(insertEvent);
            st.setLong(1, partecipation.getPerson());
            st.setLong(2, partecipation.getEvent());
            st.setDate(3, java.sql.Date.valueOf(partecipation.getDate()));
            st.executeUpdate();


        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Partecipation partecipation) {
        String query = "DELETE FROM partecipation WHERE person = ? and event=?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, partecipation.getPerson());
            st.setLong(2,partecipation.getEvent());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteByEvent(Long idEvent) throws  SQLException{
        String query = "DELETE FROM partecipation WHERE event = ?";

        PreparedStatement st = conn.prepareStatement(query);
        st.setLong(1, idEvent);
        st.executeUpdate();
        return true;

    }


    private Partecipation readPartecipation(ResultSet rs){
        try{
            Partecipation partecipation=new Partecipation();
            partecipation.setPerson(rs.getLong("person"));
            partecipation.setEvent(rs.getLong("event"));
            partecipation.setDate(rs.getDate("date").toLocalDate());
            return partecipation;
        }catch (SQLException e){e.printStackTrace();}

        return null;
    }
}
