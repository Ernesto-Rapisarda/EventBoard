package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.Partecipation;

import java.util.List;

public interface PartecipationDao {
    List<Partecipation> findAll();

    Partecipation findByPrimaryKey(Long person,Long event);

    void saveOrUpdate(Partecipation partecipation);

    void delete(Partecipation partecipation);
}
