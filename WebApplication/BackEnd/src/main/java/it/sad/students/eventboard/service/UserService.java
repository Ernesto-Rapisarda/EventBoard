package it.sad.students.eventboard.service;

import it.sad.students.eventboard.communication.EmailMessage;
import it.sad.students.eventboard.communication.EmailSenderService;
import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.*;
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
                    .role(person.getRole())
                    .is_not_locked(person.isAccountNonLocked())
                            .preferences(DBManager.getInstance().getPreferenceDao().findPreferences(person.getId()))
                            .position(DBManager.getInstance().getPositionDao().findByPrimaryKey(person.getPosition()))
                    .build()
            );
        else
            return statusCodes.notFound();
    }


    public ResponseEntity updateUser(RequestUserEdit person, String token){
        try {
            Person personDb=DBManager.getInstance().getPersonDao().findByPrimaryKey(person.getId());

            if(person==null || personDb==null )
                return statusCodes.notFound();

            if(!authorizationControll.checkOwnerAuthorization(person.getId(), token))
                return statusCodes.unauthorized();

            if(person.getPosition()!=null){
                DBManager.getInstance().getPositionDao().saveOrUpdate(person.getPosition());
            }


            if(nullOrEmpty(person.getName())||nullOrEmpty(person.getLastName())||nullOrEmpty(person.getEmail()))
                return statusCodes.commandError();          //non possono essere campi nulli

            Long tempId = null;
            Person temp = DBManager.getInstance().getPersonDao().findByEmail(person.getEmail());
            if (temp != null)
                tempId = temp.getId();
            if(tempId!=null && tempId!= person.getId())
                return statusCodes.commandError();          //se la email è gia esistente (non contato la sua vecchia nel db) ritorna errore



            //verifica email esatta
            //if(tempId!= personDb.getId())
            //    if(!sendEmail(person.getEmail(),"Cambio email effettuato","Questa email è stata associata ad un accout utente del sito GoodVibes.\n\nBuona giornata.")
            //        return statusCodes.commandError();




            if(person.getPassword()!=null && checkPassword(person.getPassword()))
                personDb.setPassword(passwordEncoder.encode(person.getPassword())); //se l'utente ha cambiato password la setto
            else if(person.getPassword()!=null)
                return statusCodes.commandError();


            personDb.setName(person.getName());
            personDb.setLastName(person.getLastName());
            personDb.setEmail(person.getEmail());
            personDb.setPosition(person.getPosition().getId());

            List<Preference> tempList = DBManager.getInstance().getPreferenceDao().findPreferences(person.getId());
            for(Preference preference: tempList)
                DBManager.getInstance().getPreferenceDao().delete(preference);

            for (Preference preference: person.getPreferences())
                DBManager.getInstance().getPreferenceDao().saveOrUpdate(preference);


            if(DBManager.getInstance().getPersonDao().saveOrUpdate(personDb))
                return statusCodes.ok();
            else
                return statusCodes.notFound();



        }catch (Exception e){
            e.printStackTrace();
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
                if(personDb.getRole()==Role.ADMIN&&!authorizationControll.checkSuperAdmin(token))
                    return statusCodes.unauthorized();

                //Il super admin non si puo eliminare da solo??
                //if(authorizationControll.checkSuperAdmin(token)&&personDb.getUsername().equals(authorizationControll.returnUsername(token)))
                //    return statusCodes.unauthorized();

                if(personDb.isEnabled())
                    sendEmail(personDb.getEmail(),
                            "Eliminazione account",
                            "Il tuo account è stato cancellato per ripetute violazioni al regolamento");

            }
            personDb.setEnabled(false);
            personDb.setIs_not_locked(false);
            personDb.setEmail(personDb.getId().toString());
            personDb.setName("Utente");
            personDb.setLastName("rimosso");
            if(DBManager.getInstance().getPersonDao().saveOrUpdate(personDb))
                return statusCodes.ok();
            else
                return statusCodes.notFound();
        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.notFound();
        }
    }

    //ban/sban
    public ResponseEntity setUserBan(RequestMotivation requestMotivation, Long id, String token) {
        // TODO: 18/01/2023 solo il super admin può bannare altri admin
        try {

            Person personDb=DBManager.getInstance().getPersonDao().findByPrimaryKey(id);
            if(personDb==null)
                return statusCodes.notFound();

            if(!authorizationControll.checkAdminAuthorization(token))
                return statusCodes.unauthorized();

            if(personDb.getUsername().equals(authorizationControll.returnUsername(token))) //non posso bannarmi da solo
                return statusCodes.unauthorized();

            if(personDb.getRole()==Role.ADMIN&&!authorizationControll.checkSuperAdmin(token))
                return statusCodes.unauthorized();



            if(!personDb.isEnabled())
                return statusCodes.notFound();

            if(personDb.isAccountNonLocked())
                personDb.setIs_not_locked(false);
            else
                personDb.setIs_not_locked(true);
            // TODO: 14/01/2023 mandare messaggio se l'account viene sbloccato?



            if(DBManager.getInstance().getPersonDao().saveOrUpdate(personDb)) {

                if(!personDb.isAccountNonLocked())
                    sendEmail(personDb.getEmail(),
                            "Ban dell'account",
                            requestMotivation.getMessage());

                return statusCodes.ok();
            }else
                return statusCodes.commandError();

        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.commandError();
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
                events.add(new ResponseEvent(event.getId(),
                        event.getDate(),
                        event.getTitle(),
                        event.getUrlPoster(),
                        event.getEventType().toString(),
                        DBManager.getInstance().getPositionDao().findByPrimaryKey(event.getPosition()) ,
                        name ));
            }

            return statusCodes.okGetElement(new ResponseOrganizer(name,person.getEmail(), events)) ;
        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.commandError();
        }
    }

    public ResponseEntity activateUser(String token) {
        Person person = DBManager.getInstance().getPersonDao().findByUsername(authorizationControll.extractUsername(token));
        person.setEnabled(true);
        DBManager.getInstance().getPersonDao().saveOrUpdate(person);
        return statusCodes.ok();
    }

    public ResponseEntity retrievePassword(String username) {
        try{
            Person person = DBManager.getInstance().getPersonDao().findByUsername(username);
            person.setPassword(passwordEncoder.encode("password"));
            DBManager.getInstance().getPersonDao().saveOrUpdate(person);

            sendEmail(person.getEmail()
                    ,"Reset Password"
                    ,"La tua nuova password è:\n\npassword\n\nSei pregato di cambiare la tua password una volta fatto login.");

            return statusCodes.ok();
        }catch (Exception exception){
            exception.printStackTrace();
            return statusCodes.commandError();
        }

    }

    public ResponseEntity<Iterable<ResponsePerson>> getPersons(String token) {
        if(!authorizationControll.checkAdminAuthorization(token))
            return statusCodes.unauthorized();
        List<Person> people = DBManager.getInstance().getPersonDao().findAll();
        List<ResponsePerson> responsePeople = new ArrayList<>();
        for (Person person:people){
            ResponsePerson responsePerson = new ResponsePerson();
            responsePerson.setId(person.getId());
            responsePerson.setName(person.getName());
            responsePerson.setLastName(person.getLastName());
            responsePerson.setUsername(person.getUsername());
            responsePerson.setEmail(person.getEmail());
            responsePerson.setRole(person.getRole());
            responsePerson.setIs_not_locked(person.isAccountNonLocked());
            responsePerson.setPreferences(DBManager.getInstance().getPreferenceDao().findPreferences(person.getId()));
            responsePerson.setPosition(DBManager.getInstance().getPositionDao().findByPrimaryKey(person.getPosition()));
            responsePeople.add(responsePerson);

        }

        return statusCodes.okGetElements(responsePeople);

    }

    public ResponseEntity promoteToAdmin(Long id, String token){
        // TODO: 18/01/2023 solo l'admin principale con la email del sito, può promuovere ad admin, e inviare email di notifica all'utente promosso
        try {
            if (!authorizationControll.checkSuperAdmin(token))  //controllo se è super admin
                return statusCodes.unauthorized();

            Person person= DBManager.getInstance().getPersonDao().findByPrimaryKey(id);
            if(person==null)
                return statusCodes.notFound();

            if(!person.isEnabled())
                return statusCodes.notFound();

            person.setRole(Role.ADMIN);

            if(DBManager.getInstance().getPersonDao().saveOrUpdate(person)) {
                sendEmail(person.getEmail(),"Promozione ad Admin","Utente "+person.getUsername()+" sei stato promosso ad admin");
                return statusCodes.ok();
            }
            else
                return statusCodes.commandError();


        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.notFound();
        }
    }

    // FUNCTION EXTRA
    private boolean nullOrEmpty(String string){
        return string==null||string.equals("");
    }
    private boolean nullOrNegative(Integer num){
        return num==null||num<0;
    }
    private boolean nullOrNegative(Double num){
        return num==null||num<0;
    }

    private boolean sendEmail(String email,String subject, String message) throws Exception{
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setTo(email);
        emailMessage.setSubject(subject);
        emailMessage.setMessage(message);
        return emailSenderService.sendEmail(emailMessage);
    }


    private boolean checkPassword(String password){
        //return password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$");
        //return password.matches("^[A-Za-z][A-Za-z1-9\\!\\_]{7,}$");
        return password.matches("^.{8,}$"); // TODO: 09/01/2023 CONTROLLA
    }



}
