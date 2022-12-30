package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.IdBroker;
import it.sad.students.eventboard.persistenza.dao.RoleDao;
import it.sad.students.eventboard.persistenza.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoPostgress implements RoleDao {
    Connection conn;
    public RoleDaoPostgress(Connection conn) {
        this.conn = conn;
    }
    @Override
    public List<Role> findAll() {
        ArrayList<Role> roles = new ArrayList<>();
        String query ="select * from role";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Role role = readRole(rs);
                if(role!=null)
                    roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public Role findByPrimaryKey(Long id) {
        String query = "select * from role where id=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Role role = readRole(rs);
                if(role!=null)
                    return role;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveOrUpdate(Role role) {
        String insertEvent = "INSERT INTO role VALUES (?,?)";
        String updateStr = "UPDATE role set name=? where id = ?";

        PreparedStatement st=null;
        try {

            if (role.getId() == null) {

                st = conn.prepareStatement(insertEvent);
                Long newId = IdBroker.getNewRoleID(conn);
                role.setId(newId);
                st.setLong(1, role.getId());
                st.setString(2, role.getName());
                st.executeUpdate();

            }else {

                st = conn.prepareStatement(updateStr);
                st.setString(1, role.getName());
                st.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Role role) {
        String query = "DELETE FROM role WHERE id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, role.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Role readRole(ResultSet rs){
        try{
            Role role=new Role();
            role.setId(rs.getLong("id"));
            role.setName(rs.getString("name"));
            return role;
        }catch (SQLException e){e.printStackTrace();}

        return null;
    }
}
