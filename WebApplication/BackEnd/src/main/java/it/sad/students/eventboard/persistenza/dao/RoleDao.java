package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.Partecipation;
import it.sad.students.eventboard.persistenza.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> findAll();

    Role findByPrimaryKey(Long id);

    void saveOrUpdate(Role role);

    void delete(Role role);
}
