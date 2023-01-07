package it.sad.students.eventboard.controller.api;


import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.Person;
import it.sad.students.eventboard.security.auth.AuthenticationService;
import it.sad.students.eventboard.security.config.JwtService;
import it.sad.students.eventboard.service.EventService;
import it.sad.students.eventboard.service.ResponseEventCreation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

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





    //update
    //delete
    //findall
    //finbbytype
}
