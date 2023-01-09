package it.sad.students.eventboard.service.httpbody;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;


@Service
@AllArgsConstructor
public class StatusCodes {

    public ResponseEntity unauthorized(){return ResponseEntity.badRequest().body("Unauthorized access to the resource");}
    public ResponseEntity commandError(){return ResponseEntity.badRequest().body("Operation not executable");}


    public ResponseEntity notFound(){return ResponseEntity.notFound().build();}


    public ResponseEntity ok(){return  ResponseEntity.ok("Success"); }
    public <T> ResponseEntity<T> okGetElement(T element){return ResponseEntity.ok(element);}
    public <T> ResponseEntity<Iterable<T>> okGetElements(Iterable<T> elements){return ResponseEntity.ok(elements);}


}
