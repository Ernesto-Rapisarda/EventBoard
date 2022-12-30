package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.IdBroker;
import it.sad.students.eventboard.persistenza.dao.PersonDao;
import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDaoPostgress implements PersonDao {
    Connection conn;
    public PersonDaoPostgress(Connection conn) {
        this.conn = conn;
    }
    @Override
    public List<Person> findAll() {
        ArrayList<Person> people = new ArrayList<>();
        String query ="select * from person";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Person person = setPerson(rs);
                if(person!=null)
                    people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    @Override
    public Person findByPrimaryKey(Long id) {
        String query = "select * from person where id=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Person person = setPerson(rs);
                if(person!=null)
                    return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveOrUpdate(Person person) {
        if (person.getId() == null) {
            String insertEvent = "INSERT INTO event VALUES (?, ?, ?, ?,?,?,?,?,?)";
            PreparedStatement st;
            try {
                st = conn.prepareStatement(insertEvent);
                Long newId = IdBroker.getNewPersonID(conn);
                person.setId(newId);

                st.setLong(1, person.getId());
                st.setString(2, person.getName());
                st.setString(3, person.getLastName());
                st.setString(4, person.getUsername());
                st.setString(5, person.getEmail());
                st.setBoolean(6, person.getActiveStatus());
                st.setLong(7, person.getPosition());
                st.setLong(8, person.getRole());
                st.setString(9, person.getPassword());

                st.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            String updateStr = "UPDATE ristorante set name=?,lastname=?,username=?,email=?,active_status=?,position=?,role=?,password=? where id = ?";

            PreparedStatement st;
            try {
                st = conn.prepareStatement(updateStr);

                st.setString(1, person.getName());
                st.setString(2,person.getLastName());
                st.setString(3, person.getUsername());
                st.setString(4, person.getEmail());
                st.setBoolean(5, person.getActiveStatus());
                st.setLong(6, person.getPosition());
                st.setLong(7, person.getRole());
                st.setString(8, person.getPassword());

                st.setLong(9, person.getId());


                st.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void delete(Person person) {
        String query = "DELETE FROM person WHERE id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, person.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private Person setPerson(ResultSet rs){
        try{
            Person person=new Person();
            person.setId(rs.getLong("id"));
            person.setName(rs.getString("name"));
            person.setLastName(rs.getString("lastname"));
            person.setUsername(rs.getString("username"));
            person.setEmail(rs.getString("email"));
            person.setActiveStatus(rs.getBoolean("active_status"));
            person.setPosition(rs.getLong("position"));
            person.setRole(rs.getLong("role"));
            person.setPassword(rs.getString("password"));
            return person;
        }catch (SQLException e){e.printStackTrace();}

        return null;
    }
}
