package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.Preference;

import java.util.List;

public interface PreferenceDao {
    List<Preference> findAll();

    Preference findByPrimaryKey(Long person,Long event_type);

    void saveOrUpdate(Preference preference);

    void delete(Preference preference);
}
