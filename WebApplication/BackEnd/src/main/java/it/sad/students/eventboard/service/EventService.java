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

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public ResponseEntity<ResponseEventCreation> createEvent(Event event,String token) {
        if (event==null)
            return ResponseEntity.notFound().build();

        event.setDate(LocalDate.from(LocalDateTime.now()));


        if(authorizationControll.checkOwnerAuthorization(event.getOrganizer(),token) && DBManager.getInstance().getEventDao().saveOrUpdate(event))
            return ResponseEntity.ok(ResponseEventCreation.builder().id(event.getId()).build()) ;
        else
            return ResponseEntity.badRequest().build();

    }

    public ResponseEntity deleteEvent (Long id, String token){


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
