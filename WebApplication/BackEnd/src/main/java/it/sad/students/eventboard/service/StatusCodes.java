package it.sad.students.eventboard.service;


import it.sad.students.eventboard.persistenza.DBManager;
import org.springframework.http.ResponseEntity;


public class StatusCodes {
    private static StatusCodes instance = null;
    public static StatusCodes getInstance() {
        if (instance == null) {
            instance = new StatusCodes();
        }
        return instance;
    }
    private StatusCodes() {}


    public ResponseEntity unauthorized(){return ResponseEntity.badRequest().body("Unauthorized access to the resource");}
    public ResponseEntity ok(){return  ResponseEntity.ok("Success"); }

}
