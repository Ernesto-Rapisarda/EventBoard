package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.EventType;
import it.sad.students.eventboard.persistenza.model.Preference;

import java.util.List;

public interface PreferenceDao {
    List<Preference> findAll();

    List<Preference> findPreferences(Long person);

    Preference findByPrimaryKey(Long person, EventType event_type);

    void saveOrUpdate(Preference preference);

    void delete(Preference preference);
}
