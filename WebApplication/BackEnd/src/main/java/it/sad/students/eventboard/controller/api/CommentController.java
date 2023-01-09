package it.sad.students.eventboard.controller.api;

import it.sad.students.eventboard.persistenza.model.Comment;
import it.sad.students.eventboard.service.InteractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CommentController {

    private final InteractionService interactionService;

    @PostMapping("/api/comment/add")
    public ResponseEntity addComment(@RequestBody Comment comment, @RequestHeader(name="Authorization") String token){
        return interactionService.addComment(comment,token);
    }

    @RequestMapping(value = "/api/comment/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity deleteComment(@PathVariable Long id,@RequestHeader (name="Authorization") String token){
        return interactionService.deleteComment(id,token);

    }

    @RequestMapping(value = "/api/comment/update", method = RequestMethod.PUT)
    public ResponseEntity updateComment(@RequestBody Comment comment, @RequestHeader(name="Authorization") String token){
        //codice 403 ...utente non loggato
        //comment non trovato o passato vuoto errore not found 404
        //non sei il proprietario del commento o un admin 400
        //codice 200, commento modificato
        //code 400 , bad request, errore nel salvataggio nel db

        return interactionService.updateComment(comment,token);
    }

    @RequestMapping(value ="/api/comment/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long id){
        //codice 403 ...utente non loggato
        //comment non trovato not found 404
        //codice 200, e restituisce il commento
        //code 400 , bad request, è stato passato id null
        return interactionService.getEvent(id);
    }



}
