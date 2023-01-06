package it.sad.students.eventboard.controller.api;

import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.Person;
import it.sad.students.eventboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/api/user/{username}")
    public ResponseEntity<Person> getPerson(@PathVariable String username){
        return ResponseEntity.ok(userService.getPerson(username));
    }

    //mette e rimuove like
    public ResponseEntity<Boolean> setLike(){
        return null;
    }

    public ResponseEntity<Boolean> setPartecipation(){
        return null;
    }

    public ResponseEntity<Boolean> addComment(){
        return null;
    }

    public ResponseEntity<Boolean> addReviews(){
        return null;
    }


    public ResponseEntity<Boolean> removeComment(){
        return null;
    }


    public ResponseEntity<Boolean> removeReviews(){
        return null;
    }




}
