package it.sad.students.eventboard.controller.api;

import it.sad.students.eventboard.service.InteractionService;
import it.sad.students.eventboard.service.RequestPersonEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class LikeController {

    private final InteractionService interactionService;

    @PostMapping("/api/like")
    public ResponseEntity setLike(@RequestBody RequestPersonEvent pe,@RequestHeader(name="Authorization") String token){
        //utente non autorizzato 403`
        //utente non proprietario di quel account 400`
        //evento non trovato 404 not found`
        //like inserito/eliminato codice 200 ok`
        return interactionService.setLike(pe.getPerson(),pe.getEvent(),token);
    }
}
