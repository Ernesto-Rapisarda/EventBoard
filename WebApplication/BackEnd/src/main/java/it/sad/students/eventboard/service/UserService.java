package it.sad.students.eventboard.service;

import it.sad.students.eventboard.communication.EmailMessage;
import it.sad.students.eventboard.communication.EmailSenderService;
import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.*;
import it.sad.students.eventboard.service.custom.*;
import it.sad.students.eventboard.service.custom.request.RequestCancellation;
import it.sad.students.eventboard.service.custom.request.RequestMotivation;
import it.sad.students.eventboard.service.custom.request.RequestUserEdit;
import it.sad.students.eventboard.service.custom.response.ResponseEvent;
import it.sad.students.eventboard.service.custom.response.ResponseOrganizer;
import it.sad.students.eventboard.service.custom.response.ResponsePerson;
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

    private final AuthorizationService authorizationService;
    private final StatusCodes statusCodes;
    private final EmailSenderService emailSenderService;
    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;



    public ResponseEntity<ResponsePerson> getPerson(String username) {
        try{
            Person person= DBManager.getInstance().getPersonDao().findByUsername(username);

            if(person!=null){

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
                        .position(getPosition(person.getPosition()))
                        .build()
                );
            }
            else
                return statusCodes.notFound();
        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.commandError();
        }
    }


    public ResponseEntity updateUser(RequestUserEdit person, String token){
        try {
            Person personDb=DBManager.getInstance().getPersonDao().findByPrimaryKey(person.getId());

            if(person==null || personDb==null )
                return statusCodes.notFound();

            if(!authorizationService.checkOwnerAuthorization(person.getId(), token))
                return statusCodes.unauthorized();

            if(person.getPosition()!=null){
                DBManager.getInstance().getPositionDao().saveOrUpdate(person.getPosition());
                personDb.setPosition(person.getPosition().getId());
            }

            if(nullOrEmpty(person.getName()) || nullOrEmpty(person.getLastName()) || nullOrEmpty(person.getEmail()))
                return statusCodes.commandError();          //non possono essere campi nulli

            Long tempId = null;
            Person temp = DBManager.getInstance().getPersonDao().findByEmail(person.getEmail());
            if (temp != null)
                tempId = temp.getId();
            if(tempId!=null && tempId!= person.getId())
                return statusCodes.commandError();          //se la email è gia esistente (non contato la sua vecchia nel db) ritorna errore


            if(person.getPassword()!=null && checkPassword(person.getPassword()))
                personDb.setPassword(passwordEncoder.encode(person.getPassword())); //se l'utente ha cambiato password la setto
            else if(person.getPassword()!=null)
                return statusCodes.commandError();

            personDb.setName(person.getName());
            personDb.setLastName(person.getLastName());
            personDb.setEmail(person.getEmail());


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
            if(!authorizationService.checkOwnerOrAdminAuthorization(requestCancellation.getId(), token))
                return statusCodes.unauthorized();

            Person personDb=DBManager.getInstance().getPersonDao().findByPrimaryKey(requestCancellation.getId());
            if(personDb == null)
                return statusCodes.notFound();

            if(!authorizationService.checkAdminAuthorization(token)){
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                personDb.getUsername(),
                                requestCancellation.getPassword()
                        )
                );
            }
            else{
                if(personDb.getRole() == Role.ADMIN && !authorizationService.checkSuperAdmin(token))
                    return statusCodes.unauthorized();

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
        try {

            Person personDb=DBManager.getInstance().getPersonDao().findByPrimaryKey(id);
            if(personDb == null)
                return statusCodes.notFound();

            if(!authorizationService.checkAdminAuthorization(token))
                return statusCodes.unauthorized();

            if(personDb.getUsername().equals(authorizationService.returnUsername(token))) //non posso bannarmi da solo
                return statusCodes.unauthorized();

            if(personDb.getRole() == Role.ADMIN && !authorizationService.checkSuperAdmin(token))
                return statusCodes.unauthorized();

            if(!personDb.isEnabled())
                return statusCodes.notFound();

            if(personDb.isAccountNonLocked())
                personDb.setIs_not_locked(false);
            else
                personDb.setIs_not_locked(true);

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

    public ResponseEntity<ResponseOrganizer> getOrganizer(Long id){
        try {
            Person person=DBManager.getInstance().getPersonDao().findByPrimaryKey(id);
            if(person == null)
                return statusCodes.notFound();

            if(!person.getRole().toString().equals("ORGANIZER"))
                return statusCodes.unauthorized();

            String name= person.getName()+" "+person.getLastName();

            List<Event> fullEvent=DBManager.getInstance().getEventDao().findByOrganizer(id);
            if(fullEvent == null)
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
        Person person = DBManager.getInstance().getPersonDao().findByUsername(authorizationService.extractUsername(token));
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
        if(!authorizationService.checkAdminAuthorization(token))
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
            responsePerson.setPosition(getPosition(person.getPosition()));
            responsePeople.add(responsePerson);

        }

        return statusCodes.okGetElements(responsePeople);

    }

    public ResponseEntity promoteToAdmin(Long id, String token){
        try {
            if (!authorizationService.checkSuperAdmin(token))  //controllo se è super admin
                return statusCodes.unauthorized();

            Person person= DBManager.getInstance().getPersonDao().findByPrimaryKey(id);
            if(person == null)
                return statusCodes.notFound();

            if(!person.isEnabled())
                return statusCodes.notFound();

            if(person.getRole() == Role.ADMIN)
                person.setRole(Role.USER);
            else if(person.getRole() == Role.USER)
                person.setRole(Role.ADMIN);
            else
                return statusCodes.commandError();


            if(DBManager.getInstance().getPersonDao().saveOrUpdate(person)){
                if(person.getRole() == Role.ADMIN)
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
        return string == null || string.equals("");
    }
    private boolean nullOrNegative(Integer num){
        return num == null || num<0;
    }
    private boolean nullOrNegative(Double num){
        return num == null || num<0;
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
        return password.matches("^.{8,}$");
    }

    private Position getPosition(Long id){
        Position position = new Position();
        if(id != null)
            position = DBManager.getInstance().getPositionDao().findByPrimaryKey(id);

        return position;
    }



}
