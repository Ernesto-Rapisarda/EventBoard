package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.Partecipation;

import java.sql.SQLException;
import java.util.List;

public interface PartecipationDao {
    List<Partecipation> findAll();

    Partecipation findByPrimaryKey(Long person,Long event);

    void saveOrUpdate(Partecipation partecipation);

    void delete(Partecipation partecipation);

    boolean deleteByEvent(Long idEvent) throws SQLException;
}
