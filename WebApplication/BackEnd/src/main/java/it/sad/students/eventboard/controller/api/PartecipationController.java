package it.sad.students.eventboard.controller.api;

import it.sad.students.eventboard.service.InteractionService;
import it.sad.students.eventboard.service.custom.request.RequestPersonEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PartecipationController {

    private final InteractionService interactionService;

    @PostMapping("/api/partecipation")
    public ResponseEntity setPartecipation(@RequestBody RequestPersonEvent pe,@RequestHeader(name="Authorization") String token){
        //utente non autorizzato 403`
        //utente non proprietario di quel account 400`
        //evento non trovato 404 not found`
        //partecipation inserita/eliminata codice 200 ok`
        return interactionService.setPartecipation(pe.getPerson(), pe.getEvent(),token);
    }

}
