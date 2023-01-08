package it.sad.students.eventboard.service;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Person;
import it.sad.students.eventboard.persistenza.model.Position;
import it.sad.students.eventboard.security.auth.AuthorizationControll;
import it.sad.students.eventboard.security.config.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService { //utente loggato

    private final AuthorizationControll authorizationControll;
    private final StatusCodes statusCodes;

    private final PasswordEncoder passwordEncoder;


    public Person getPerson(String username) {

        Person person= DBManager.getInstance().getPersonDao().findByUsername(username);

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
                DBManager.getInstance().getLikeDao().findByPerson(person.getId())
                //DBManager.getInstance().getLikeDao().findAll()
        );

        return person;
    }

    //nome casomai da cambiare
    public ResponseEntity<Person> editUser(Person person,String token){
        try {
            if(!authorizationControll.checkOwnerOrAdminAuthorization(person.getId(), token))
                return statusCodes.unauthorized();

            Person personDb=DBManager.getInstance().getPersonDao().findByPrimaryKey(person.getId());

            if(!person.getUsername().equals(personDb.getUsername()))
                return statusCodes.commandError();          //se l'username risulta modificato non effettuo nessuna modifica

            if(person.getPosition()!=null){
                Position position=DBManager.getInstance().getPositionDao().findByPrimaryKey(person.getPosition());
                if(position==null)
                    return statusCodes.commandError();      //se la posizione è stata inserita deve essere presente nel db????
            }

            if(person.getName()==null||person.getEmail()==null||person.getLastName()==null)
                return statusCodes.commandError();          //non possono essere campi nulli

            if(person.getPassword()==null)
                person.setPassword(personDb.getPassword()); //se l'utente non ha cambiato password la riprendo dal db
            else
                person.setPassword(passwordEncoder.encode(person.getPassword()));

            DBManager.getInstance().getPersonDao().saveOrUpdate(person);
            return statusCodes.ok();

            // TODO: 08/01/2023 controllare campo ruolo modificabile??
            /*
                   {
                        "id": 29, // se lo modifichi ti da errore non autorizzato
                        "name": "Alessandro", //modificabile
                        "lastName": "Monetti", //modificabile
                        "username": "Pingu",   //se lo modifichi ti da errore sul comando
                        "password": null,      //modificabile
                        "email": "pingu@fratm",  //modificabile
                        "activeStatus": true,   //default da qui in giu
                        "likes": [],
                        "comments": [],
                        "reviews": [],
                        "preferences": [],
                        "position": 1,         //modificabile
                        "role": "ORGANIZER",  //modificabile???
                        "enabled": true,
                        "accountNonExpired": true,
                        "credentialsNonExpired": true,
                        "authorities": [],
                        "accountNonLocked": true
                    }
             */

        }catch (Exception e){
            return  statusCodes.notFound();
        }
    }


    // TODO: 08/01/2023 da controllare in base a cosa si decide di fare (eliminazione accout)
    public ResponseEntity disableUser(Long id,String token){ //id person
        try {
            if(!authorizationControll.checkOwnerOrAdminAuthorization(id, token))
                return statusCodes.unauthorized();

            Person personDb=DBManager.getInstance().getPersonDao().findByPrimaryKey(id);
            if(personDb==null)
                return statusCodes.notFound();

            if(authorizationControll.checkAdminAuthorization(token) && !personDb.getActiveStatus())
                return statusCodes.commandError();

            personDb.setActiveStatus(false);
            DBManager.getInstance().getPersonDao().saveOrUpdate(personDb);
            return statusCodes.ok();
        }catch (Exception e){
            return statusCodes.notFound();
        }
    }

    public ResponseEntity enableUser(Long id,String token) { //id person
        try {
            if(!authorizationControll.checkAdminAuthorization(token))
                return statusCodes.unauthorized();

            Person personDb=DBManager.getInstance().getPersonDao().findByPrimaryKey(id);
            if(personDb.getActiveStatus())
                return statusCodes.commandError();

            personDb.setActiveStatus(true);
            DBManager.getInstance().getPersonDao().saveOrUpdate(personDb);
            return statusCodes.ok();

        }catch (Exception e){
            return statusCodes.notFound();
        }
    }
}
