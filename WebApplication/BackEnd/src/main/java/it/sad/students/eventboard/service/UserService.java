package it.sad.students.eventboard.service;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Person;
import org.springframework.stereotype.Service;

@Service
public class UserService { //utente loggato

    public Person getPerson(String username) {

        Person person= DBManager.getInstance().getPersonDao().findByUsername(username);

        person.setId(null);
        person.setPassword(null);


        person.setComments(
                DBManager.getInstance().getCommentDao().findByPerson(person.getId())
        );
        person.setReviews(
                DBManager.getInstance().getReviewDao().findByPerson(person.getId())
        );
        person.setPreferences(
                DBManager.getInstance().getPreferenceDao().findPreferences(person.getId())
        );
        person.setLikes(
                //DBManager.getInstance().getLikeDao().findByPerson(person.getId())
                DBManager.getInstance().getLikeDao().findAll()
        );

        return person;
    }
}
