package it.sad.students.eventboard.controller.api;

import it.sad.students.eventboard.service.InteractionService;
import it.sad.students.eventboard.service.RequestPersonEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PartecipationController {

    private final InteractionService interactionService;

    @PostMapping("/api/partecipation")
    public ResponseEntity setPartecipation(@RequestBody RequestPersonEvent pe){
        if(interactionService.setPartecipation(pe.getPerson(), pe.getEvent()))
            return ResponseEntity.ok("");
        return ResponseEntity.notFound().build();
    }

}
