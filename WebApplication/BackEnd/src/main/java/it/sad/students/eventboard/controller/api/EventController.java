package it.sad.students.eventboard.controller.api;


import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.EventType;
import it.sad.students.eventboard.security.auth.AuthenticationService;
import it.sad.students.eventboard.security.config.JwtService;
import it.sad.students.eventboard.service.EventService;
import it.sad.students.eventboard.service.ResponseEvent;
import it.sad.students.eventboard.service.ResponseEventCreation;
import it.sad.students.eventboard.service.ResponseEventDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class EventController {

    private final AuthenticationService service;
    private final EventService eventService;
    private final JwtService jwtService;

    @RequestMapping(value="/api/create/event",method= RequestMethod.POST)
    public ResponseEntity<ResponseEventCreation> createEvent(@RequestBody Event event, @RequestHeader (name="Authorization") String token){
        //utente non autorizzato 403
        //badrequest errore nel db nella creazione 400
        //evento nullo 404 not found
        //inserimento effettuato codice 200 ok
        return eventService.createEvent(event,token);

    }


    @RequestMapping(value="/api/delete/event/{id}",method= RequestMethod.DELETE)
    public ResponseEntity deleteEvent(@PathVariable Long id,@RequestHeader (name="Authorization") String token ){
        //utente non autorizzato 403
        //organizer non proprietario dell'evento 400
        //evento non trovato 404 not found
        //rimozione effettuata codice 200 ok
        return eventService.deleteEvent(id,token);
    }

    @RequestMapping(value = "/api/update/event", method= RequestMethod.PUT)
    public ResponseEntity updateEvent(@RequestBody Event event,@RequestHeader (name="Authorization") String token){
        //utente non autorizzato 403
        //organizer non proprietario dell'evento o modifica non riuscita 400
        //evento non trovato 404 not found
        //modifica effettuata codice 200 ok
        return eventService.updateEvent(event,token);
    }

    //get
    @RequestMapping ("/api/noauth/get/events")
    public ResponseEntity<Iterable<ResponseEvent>> getHome(){
        //errore 404 not found...lista vuota
        //codice 200 ok
        return eventService.getAllEvents();
    }
    // TODO: 05/01/2023 carichiamo tutti?senza limiti? 

    //post
    @RequestMapping("/api/noauth/events/filtered")
    public ResponseEntity<Iterable<ResponseEvent>> getHomeWithPreferences(@RequestBody List<EventType> eventTypes){

        //errore 404 not found...lista tipi di eventi vuota e/o lista filtrata vuota
        //codice 200 ok
        return eventService.getPreferredEvents(eventTypes);
    }

    //get
    @RequestMapping(value="/api/noauth/event/details/{id}")
    public ResponseEntity<ResponseEventDetails> getEvent(@PathVariable Long id ){
        //errore 404 not found...evento non trovato
        //codice 200 ok
        return eventService.getEventFullDetails(id);
    }




}
