package it.sad.students.eventboard.service;

import it.sad.students.eventboard.communication.EmailMessage;
import it.sad.students.eventboard.communication.EmailSenderService;
import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.Person;
import it.sad.students.eventboard.persistenza.model.Position;
import it.sad.students.eventboard.service.httpbody.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService { //utente loggato

    private final AuthorizationControll authorizationControll;
    private final StatusCodes statusCodes;
    private final EmailSenderService emailSenderService;
    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;



    public ResponseEntity<ResponsePerson> getPerson(String username) {

        Person person= DBManager.getInstance().getPersonDao().findByUsername(username);
        /*
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
        );*/
        if(person!=null)
            return statusCodes.okGetElement(ResponsePerson
                    .builder()
                    .id(person.getId())
                    .name(person.getName())
                    .lastName(person.getLastName())
                    .username(username)
                    .email(person.getEmail())
                    .position(person.getPosition())
                    .role(person.getRole())
                    .locked(person.isAccountNonLocked())
                            .preferences(DBManager.getInstance().getPreferenceDao().findPreferences(person.getId()))
                    .build()
            );
        else
            return statusCodes.notFound();
    }

    //nome casomai da cambiare
    // TODO: 09/01/2023  CONTROLLARE COME GESTIRE I VARI STATUS RISPETTO AD EMAIL GIA UTILIZATA ECC.
    public ResponseEntity editUser(EditRequest person, String token){
        try {
            if(person==null)
                return statusCodes.notFound();

            if(!authorizationControll.checkOwnerOrAdminAuthorization(person.getId(), token))
                return statusCodes.unauthorized();

            Person personDb=DBManager.getInstance().getPersonDao().findByPrimaryKey(person.getId());

            if(person.getPosition()!=null){
                Position position=DBManager.getInstance().getPositionDao().findByPrimaryKey(person.getPosition());
                if(position==null)
                    return statusCodes.commandError();      //se la posizione è stata inserita deve essere presente nel db
            }

            if(nullOrEmpty(person.getName())||nullOrEmpty(person.getLastName())||nullOrEmpty(person.getEmail()))
                return statusCodes.commandError();          //non possono essere campi nulli

            if(DBManager.getInstance().getPersonDao().findByEmail(person.getEmail())!=null&&!personDb.getEmail().equals(person.getEmail()))
                return statusCodes.commandError();          //se la email è gia esistente (non contato la sua vecchia nel db) ritorna errore

            if(person.getPassword()==null)
                person.setPassword(personDb.getPassword()); //se l'utente non ha cambiato password la riprendo dal db
            else if (!checkPassword(person.getPassword()))   // se è inserita male rispondo con errore
                return statusCodes.commandError();
            else                                            // se è inserita bene la cripto e la setto
                person.setPassword(passwordEncoder.encode(person.getPassword()));

            Person newPerson=new Person(
                    person.getId(),
                    person.getName(),
                    person.getLastName(),
                    personDb.getUsername(),
                    person.getPassword(),
                    person.getEmail(),
                    //personDb.getActiveStatus(),
                    person.getPosition(),
                    personDb.getRole()
            );

            if(DBManager.getInstance().getPersonDao().saveOrUpdate(newPerson))
                return statusCodes.ok();
            else
                return statusCodes.notFound();

            // TODO: 08/01/2023 controllare campo ruolo modificabile??


        }catch (Exception e){
            return  statusCodes.notFound();
        }
    }


    public ResponseEntity deleteUser(RequestCancellation requestCancellation, String token){ //id person
        try {
            if(!authorizationControll.checkOwnerOrAdminAuthorization(requestCancellation.getId(), token))
                return statusCodes.unauthorized();

            Person personDb=DBManager.getInstance().getPersonDao().findByPrimaryKey(requestCancellation.getId());
            if(personDb==null)
                return statusCodes.notFound();


            if(!authorizationControll.checkAdminAuthorization(token)){
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                personDb.getUsername(),
                                requestCancellation.getPassword()
                        )
                );
            }
            else{
                if(personDb.isEnabled()){
                    EmailMessage emailMessage = new EmailMessage();
                    emailMessage.setTo(personDb.getEmail());
                    emailMessage.setSubject("Eliminazione account");
                    emailMessage.setMessage("Il tuo account è stato cancellato per ripetute violazioni al regolamento");
                    emailSenderService.sendEmail(emailMessage);
                }
            }
            personDb.setEnabled(false);
            personDb.setLocked(false);
            personDb.setEmail(personDb.getId().toString());
            personDb.setName("Utente");
            personDb.setLastName("rimosso");
            DBManager.getInstance().getPersonDao().saveOrUpdate(personDb);
            return statusCodes.ok();
        }catch (Exception e){
            return statusCodes.notFound();
        }
    }
    public ResponseEntity setUserLock(RequestMotivation requestMotivation, Long id, String token) {
        try {
            if(!authorizationControll.checkAdminAuthorization(token))
                return statusCodes.unauthorized();

            Person personDb=DBManager.getInstance().getPersonDao().findByPrimaryKey(id);
            if(personDb==null)
                return statusCodes.notFound();
            
            if(!personDb.isEnabled())
                return statusCodes.notFound();

            if(personDb.isAccountNonLocked()){
                EmailMessage emailMessage = new EmailMessage();
                emailMessage.setTo(personDb.getEmail());
                emailMessage.setSubject("Ban dell'account");
                emailMessage.setMessage(requestMotivation.getMessage());
                emailSenderService.sendEmail(emailMessage);
                personDb.setLocked(false);
            }
            else{
                // TODO: 14/01/2023 mandare messaggio se l'account viene sbloccato?
                personDb.setLocked(true);
            }

            
            if(DBManager.getInstance().getPersonDao().saveOrUpdate(personDb))
                return statusCodes.ok();
            else
                return statusCodes.commandError();

        }catch (Exception e){
            return statusCodes.notFound();
        }
    }

/*
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
    }*/

    public ResponseEntity<ResponseOrganizer> getOrganizer(Long id){
        try {
            // TODO: 10/01/2023 CONTROLLARE SE è DISABILITATO


            Person person=DBManager.getInstance().getPersonDao().findByPrimaryKey(id);
            if(person==null)
                return statusCodes.notFound();

            if(!person.getRole().toString().equals("ORGANIZER"))
                return statusCodes.unauthorized();

            String name= person.getName()+" "+person.getLastName();

            List<Event> fullEvent=DBManager.getInstance().getEventDao().findByOrganizer(id);
            if(fullEvent==null)
                return statusCodes.notFound();

            List<ResponseEvent> events=new ArrayList<>();
            for(Event event:fullEvent){
                events.add(new ResponseEvent(event.getId(),event.getDate(),event.getTitle(), event.getUrlPoster(), event.getEventType().toString(), event.getPosition(),name ));
            }

            return statusCodes.okGetElement(new ResponseOrganizer(name,person.getEmail(), events)) ;
        }catch (Exception e){
            return statusCodes.notFound();
        }
    }

    public String activateUser(String token) {
        System.out.println(token);
        Person person = DBManager.getInstance().getPersonDao().findByUsername(authorizationControll.extractUsername(token));
        person.setEnabled(true);
        DBManager.getInstance().getPersonDao().saveOrUpdate(person);
        return "FUNZIONA";
    }

    public ResponseEntity retrievePassword(String username) {
        try{
            Person person = DBManager.getInstance().getPersonDao().findByUsername(username);
            person.setPassword(passwordEncoder.encode("password"));
            DBManager.getInstance().getPersonDao().saveOrUpdate(person);
            EmailMessage emailMessage = new EmailMessage();
            emailMessage.setTo(person.getEmail());
            emailMessage.setSubject("Reset Password");
            emailMessage.setMessage("La tua nuova password è:\n\npassword\n\nSei pregato di cambiare la tua password una volta fatto login.");
            emailSenderService.sendEmail(emailMessage);
            return statusCodes.ok();
        }catch (Exception exception){
            exception.printStackTrace();
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
        return password.matches("^.{8,}$"); // TODO: 09/01/2023 CONTROLLA
    }



}
