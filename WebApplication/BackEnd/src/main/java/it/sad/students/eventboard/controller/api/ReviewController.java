package it.sad.students.eventboard.controller.api;

import it.sad.students.eventboard.persistenza.model.Review;
import it.sad.students.eventboard.service.InteractionService;
import it.sad.students.eventboard.service.httpbody.RequestPersonEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ReviewController {

    private final InteractionService interactionService;

    @PostMapping("api/review/add")
    public ResponseEntity addReviews(@RequestBody Review review, @RequestHeader(name="Authorization") String token){
        //utente non autorizzato 403`
        //utente non proprietario di quel account 400`
        //evento non trovato, errore 404 not found`
        //review aggiunta codice 200 ok`
        return interactionService.addReview(review,token);
    }

    @RequestMapping(value = "api/review/delete",method = RequestMethod.DELETE)
    public ResponseEntity deleteReviews(@RequestBody RequestPersonEvent pe, @RequestHeader (name="Authorization") String token){
        //utente non autorizzato 403`
        //utente non proprietario di quel account/oppure non Admin 400`
        //evento non trovato, errore 404 not found`
        //review rimossa codice 200 ok`
        return interactionService.deleteReview(pe.getPerson(),pe.getEvent(),token);
    }

    @RequestMapping(value = "api/review/edit",method = RequestMethod.PUT)
    public ResponseEntity updateReview(@RequestBody Review review, @RequestHeader (name="Authorization") String token){
        //utente non autorizzato 403`
        //utente non proprietario di quel account 400`
        //errore campi modificati, errore server 404 not found`
        //utente modificato aggiunto codice 200 ok`
        return interactionService.updateReview(review,token);
    }


    @RequestMapping(value="api/review",method = RequestMethod.POST)
    public ResponseEntity getReview(@RequestBody RequestPersonEvent requestPersonEvent,@RequestHeader (name="Authorization") String token){
        //utente non autorizzato 403`
        //errore campi nulli 400`
        //errore review non esistente, errore server 404 not found`
        //utente modificato aggiunto codice 200 ok`
        return interactionService.getReview(requestPersonEvent.getPerson(),requestPersonEvent.getEvent());
    }

}
