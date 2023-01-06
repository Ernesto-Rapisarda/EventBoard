package it.sad.students.eventboard.controller.api;


import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.Person;
import it.sad.students.eventboard.security.auth.AuthenticationService;
import it.sad.students.eventboard.security.config.JwtService;
import it.sad.students.eventboard.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
//@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class EventController {

    private final AuthenticationService service;
    private final EventService eventService;
    private final JwtService jwtService;

    @RequestMapping(value="/api/create/event",method= RequestMethod.POST)
    public ResponseEntity<Long> createEvent(@RequestBody Event event ){

        event.setDate(LocalDate.from(LocalDateTime.now()));

        Boolean status = eventService.createEvent(event);
        if(status)
            return ResponseEntity.ok(event.getId());
        else
            return ResponseEntity.ok(null);
    }

    @RequestMapping(value="/api/delete/event/{id}",method= RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteEvent(@PathVariable Long id,@RequestHeader (name="Authorization") String token ){
        Event event = DBManager.getInstance().getEventDao().findByPrimaryKey(id);
        String jwt = token.substring(7);

        Person person = DBManager.getInstance().getPersonDao().findByUsername(jwtService.extractUsername(jwt));
        if(event.getPublisher().equals(person.getId()) || person.getRole().toString().equals("ADMIN"))
            DBManager.getInstance().getEventDao().delete(event);

        else
            System.out.println("te lo puoi scordare");


        /*

        event.setDate(LocalDate.from(LocalDateTime.now()));

        Boolean status = eventService.createEvent(event);
        if(status)
            return ResponseEntity.ok(event.getId());
        else*/
            return ResponseEntity.ok(null);
    }

/*
    @PreAuthorize("hasRole('PUBLISHER')")
    @RequestMapping(value="/api/create/event",method= RequestMethod.POST)
    public ResponseEntity<Long> createEvent(@RequestBody Event event , String token, String username){

        if (jwtService.isTokenValid(token, DBManager.getInstance().getPersonDao().findByUsername(username)))
            event.setDate(LocalDate.from(LocalDateTime.now()));

        Boolean status = eventService.createEvent(event);
        if(status)
            return ResponseEntity.ok(event.getId());
        else
            return ResponseEntity.ok(null);
    }*/

    //update
    //delete
    //findall
    //finbbytype
}
