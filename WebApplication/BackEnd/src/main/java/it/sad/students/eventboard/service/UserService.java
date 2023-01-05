package it.sad.students.eventboard.service;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Person;
import org.springframework.stereotype.Service;

@Service
public class UserService { //utente loggato

    public Person getPerson(String username) {
        return DBManager.getInstance().getPersonDao().findByUsername(username);
    }
}
