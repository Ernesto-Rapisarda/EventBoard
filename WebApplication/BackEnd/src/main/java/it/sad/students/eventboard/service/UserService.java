package it.sad.students.eventboard.service;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Person;
import it.sad.students.eventboard.persistenza.model.Position;
import it.sad.students.eventboard.service.httpbody.StatusCodes;
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
    // TODO: 09/01/2023  UTILIZZARE RegisterRequest INVECE DI Person
    public ResponseEntity<Person> editUser(Person person,String token){
        try {
            if(person==null)
                return statusCodes.notFound();

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

            if(nullOrEmpty(person.getName())||nullOrEmpty(person.getLastName()))
                return statusCodes.commandError();          //non possono essere campi nulli

            if(person.getPassword()==null)
                person.setPassword(personDb.getPassword()); //se l'utente non ha cambiato password la riprendo dal db
            else if (!checkPassword(person.getPassword()))   // se è inserita male rispondo con errore
                return statusCodes.commandError();
            else                                            // se è inserita bene la cripto e la setto
                person.setPassword(passwordEncoder.encode(person.getPassword()));

            // MANCA AGGIUNTA CAMPI DI DEFAULT

            if(DBManager.getInstance().getPersonDao().saveOrUpdate(person))
                return statusCodes.ok();
            else
                return statusCodes.notFound();

            // TODO: 08/01/2023 controllare campo ruolo modificabile??


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


    // FUNCTION EXTRA
    private boolean nullOrEmpty(String string){
        return string==null||string.trim()=="";
    }
    private boolean nullOrNegative(Integer num){
        return num==null||num<0;
    }
    private boolean nullOrNegative(Double num){
        return num==null||num<0;
    }

    private boolean checkPassword(String password){
        //return password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$");
        //return password.matches("^[A-Za-z][A-Za-z1-9\\!\\_]{7,}$");
        return password.matches("^\\S{8,}$"); // TODO: 09/01/2023 CONTROLLA
    }
}
