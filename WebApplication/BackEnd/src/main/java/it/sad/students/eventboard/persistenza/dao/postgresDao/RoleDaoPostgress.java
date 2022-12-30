package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.dao.RoleDao;
import it.sad.students.eventboard.persistenza.model.Partecipation;
import it.sad.students.eventboard.persistenza.model.Role;

import java.util.List;

public class RoleDaoPostgress implements RoleDao {
    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public Partecipation findByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public void saveOrUpdate(Role role) {

    }

    @Override
    public void delete(Role role) {

    }
}
