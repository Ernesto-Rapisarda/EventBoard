package it.sad.students.eventboard.controller.api;

import it.sad.students.eventboard.persistenza.model.Review;
import it.sad.students.eventboard.service.InteractionService;
import it.sad.students.eventboard.service.RequestPersonEvent;
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
        return interactionService.addReview(review,token);
    }

    @RequestMapping(value = "api/review/delete",method = RequestMethod.DELETE)
    public ResponseEntity deleteReviews(@RequestBody RequestPersonEvent pe, @RequestHeader (name="Authorization") String token){
        return interactionService.deleteReview(pe.getPerson(),pe.getEvent(),token);
    }

    // TODO: 09/01/2023 EDIT REVIEW DA FARE

    // TODO: 09/01/2023 Get singolo review


}
