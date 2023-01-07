package it.sad.students.eventboard.service;


import it.sad.students.eventboard.persistenza.DBManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;


public class StatusCodes {

    public ResponseEntity unauthorized(){return ResponseEntity.badRequest().body("Unauthorized access to the resource");}
    public ResponseEntity ok(){return  ResponseEntity.ok("Success"); }
    public ResponseEntity notFound(){return ResponseEntity.notFound().build();}

}
