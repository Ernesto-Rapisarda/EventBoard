package it.sad.students.eventboard.service;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.EventType;
import it.sad.students.eventboard.persistenza.model.Person;
import it.sad.students.eventboard.security.auth.AuthorizationControll;
import it.sad.students.eventboard.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final JwtService jwtService;
    private final AuthorizationControll authorizationControll;
    public Iterable<Event> getAllEvents(){
        return DBManager.getInstance().getEventDao().findAll();
    }

    public Iterable<Event> getPreferredEvents(List<EventType> eventTypes) {

        List<Event> events = new ArrayList<>();
        for (EventType eventType: eventTypes){
            events.addAll(DBManager.getInstance().getEventDao().findByType(eventType.getId()));
        }

        return  events;
    }

    public Boolean createEvent(Event event) {


        return DBManager.getInstance().getEventDao().saveOrUpdate(event);
    }

    public ResponseEntity deleteEvent (Long id, String token){
        //utente non autorizzato 403
        //organizer non proprietario dell'evento 400
        //evento non trovato 404 not found
        //rimozione effettuata codice 200 ok

        Event event = DBManager.getInstance().getEventDao().findByPrimaryKey(id);
        if (event==null)
            return ResponseEntity.notFound().build();


        if(authorizationControll.checkOwnerOrAdminAuthorization(event.getOrganizer(), token)){
            DBManager.getInstance().getEventDao().delete(event);
            return ResponseEntity.ok("Rimozione effettuata");
        }
        else
            return ResponseEntity.badRequest().body("Non hai i permessi per rimuovere l'evento");
    }
}
