package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.dao.PreferenceDao;
import it.sad.students.eventboard.persistenza.model.Preference;
import it.sad.students.eventboard.persistenza.model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreferenceDaoPostgress implements PreferenceDao {

    Connection conn;
    public PreferenceDaoPostgress(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Preference> findAll() {
        ArrayList<Preference> preferences = new ArrayList<>();
        String query ="select * from preferences";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Preference preference = readPreference(rs);
                if(preference!=null)
                    preferences.add(preference);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preferences;
    }

    @Override
    public Preference findByPrimaryKey(Long person,Long event_type) {
        String query = "select * from preferences where person = ? and event_type=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, person);
            stmt.setLong(2,event_type);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Preference preference = readPreference(rs);
                if(preference!=null)
                    return preference;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveOrUpdate(Preference preference) {
        String insertEvent = "INSERT INTO preferences VALUES (?,?)";

        PreparedStatement st=null;
        try {
            if (preference.getPerson() == null&&preference.getEvent_type() == null) {
                st = conn.prepareStatement(insertEvent);
                st.setLong(1, preference.getPerson());
                st.setLong(2, preference.getEvent_type());
                st.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Preference preference) {
        String query = "DELETE FROM preferences WHERE person = ? and event_type=?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, preference.getPerson());
            st.setLong(2,preference.getEvent_type());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Preference readPreference(ResultSet rs){
        try{
            Preference preference=new Preference();
            preference.setPerson(rs.getLong("person"));
            preference.setEvent_type(rs.getLong("event_type"));
            return preference;
        }catch (SQLException e){e.printStackTrace();}

        return null;
    }
}
