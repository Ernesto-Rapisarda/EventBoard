package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.IdBroker;
import it.sad.students.eventboard.persistenza.dao.PersonDao;
import it.sad.students.eventboard.persistenza.model.Person;
import it.sad.students.eventboard.persistenza.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                Person person = readPerson(rs);
                if(person!=null)
                    people.add(person);
            }
        } catch (SQLException e) {
            return null;
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
                Person person = readPerson(rs);
                if(person!=null)
                    return person;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public Person findByUsername (String username){
        String query = "select * from person where username=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Person person = readPerson(rs);
                if(person!=null)
                    return person;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public Person findByEmail(String email) {
        String query = "select * from person where email=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Person person = readPerson(rs);
                if(person!=null)
                    return person;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(Person person) {
        String insertEvent = "INSERT INTO person VALUES (?,?,?,?,?,?,?,?,?,?)";
        String updateStr = "UPDATE person set name=?,lastname=?,username=?,email=?,enabled=?,position=?,role=?,password=?,is_not_locked=? where id = ?";

        PreparedStatement st=null;
        try {

            if (person.getId() == null) {

                st = conn.prepareStatement(insertEvent);
                Long newId = IdBroker.getNewPersonID(conn);
                person.setId(newId);
                st.setLong(1, person.getId());
                st.setString(2, person.getName());
                st.setString(3, person.getLastName());
                st.setString(4, person.getUsername());
                st.setString(5, person.getEmail());
                st.setBoolean(6, person.isEnabled());
                st.setObject(7, person.getPosition());          // long o null
                st.setString(8, person.getRole().toString());
                st.setString(9, person.getPassword());
                st.setBoolean(10,person.isAccountNonLocked());
                st.executeUpdate();

            }else {

                st = conn.prepareStatement(updateStr);
                st.setString(1, person.getName());
                st.setString(2,person.getLastName());
                st.setString(3, person.getUsername());
                st.setString(4, person.getEmail());
                st.setBoolean(5, person.isEnabled());
                st.setObject(6, person.getPosition());          // long o null
                st.setString(7, person.getRole().toString());
                st.setString(8, person.getPassword());
                st.setBoolean(9,person.isAccountNonLocked());
                st.setLong(10, person.getId());
                st.executeUpdate();
            }
            return true;
        }catch (SQLException e) {
            return false;
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
            
        }
    }



    private Person readPerson(ResultSet rs){
        try{
            Person person=new Person();
            person.setId(rs.getLong("id"));
            person.setName(rs.getString("name"));
            person.setLastName(rs.getString("lastname"));
            person.setUsername(rs.getString("username"));
            person.setEmail(rs.getString("email"));
            person.setEnabled(rs.getBoolean("enabled"));
            person.setPosition(rs.getObject("position",Long.class));
            person.setRole(Role.valueOf(rs.getString("role")));
            person.setPassword(rs.getString("password"));
            person.setIs_not_locked(rs.getBoolean("is_not_locked"));
            return person;
        }catch (SQLException e){}

        return null;
    }


}
