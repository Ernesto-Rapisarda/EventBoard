package it.sad.students.eventboard.controller.api;

import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.Person;
import it.sad.students.eventboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService eventService;

    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/user")
    public ResponseEntity<Person> getHomeWithPreferences(@PathVariable String username){
        return ResponseEntity.ok(eventService.getPerson(username));
    }
}
