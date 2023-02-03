package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.dao.PreferenceDao;
import it.sad.students.eventboard.persistenza.model.EventType;
import it.sad.students.eventboard.persistenza.model.Preference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            return null;
        }
        return preferences;
    }

    @Override
    public List<Preference> findPreferences(Long person) {
        ArrayList<Preference> preferences = new ArrayList<>();
        String query ="select * from preferences where person=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1,person);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Preference preference = readPreference(rs);
                if(preference!=null)
                    preferences.add(preference);
            }
        } catch (SQLException e) {
            return null;
        }
        return preferences;
    }

    @Override
    public Preference findByPrimaryKey(Long person, EventType event_type) {
        String query = "select * from preferences where person = ? and event_type=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, person);
            stmt.setString(2,event_type.toString());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Preference preference = readPreference(rs);
                if(preference!=null)
                    return preference;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public void saveOrUpdate(Preference preference) {
        String insertEvent = "INSERT INTO preferences VALUES (?,?)";

        PreparedStatement st=null;
        try {
                st = conn.prepareStatement(insertEvent);
                st.setLong(1, preference.getPerson());
                st.setString(2, preference.getEvent_type().toString());
                st.executeUpdate();

        }catch (SQLException e) {
            
        }
    }

    @Override
    public void delete(Preference preference) {
        String query = "DELETE FROM preferences WHERE person = ? and event_type=?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, preference.getPerson());
            st.setString(2,preference.getEvent_type().toString());
            st.executeUpdate();
        } catch (SQLException e) {
            
        }
    }
    private Preference readPreference(ResultSet rs){
        try{
            Preference preference=new Preference();
            preference.setPerson(rs.getLong("person"));
            preference.setEvent_type(EventType.valueOf(rs.getString("event_type")));
            return preference;
        }catch (SQLException e){}

        return null;
    }
}
