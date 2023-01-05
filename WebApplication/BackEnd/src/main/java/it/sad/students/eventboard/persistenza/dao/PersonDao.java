package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.Person;

import java.util.List;

public interface PersonDao {
    List<Person> findAll();

    Person findByPrimaryKey(Long id);
    Person findByUsername (String username);
    Person findByEmail (String email);

    void saveOrUpdate(Person person);

    void delete(Person person);
}
