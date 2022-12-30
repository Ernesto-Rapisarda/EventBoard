package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.dao.PersonDao;
import it.sad.students.eventboard.persistenza.model.Person;

import java.util.List;

public class PersonDaoPostgress implements PersonDao {
    @Override
    public List<Person> findAll() {
        return null;
    }

    @Override
    public Person findByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public void saveOrUpdate(Person person) {

    }

    @Override
    public void delete(Person person) {

    }
}
