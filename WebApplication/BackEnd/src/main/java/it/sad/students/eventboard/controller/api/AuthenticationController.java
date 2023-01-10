package it.sad.students.eventboard.controller.api;

import it.sad.students.eventboard.service.httpbody.AuthenticationRequest;
import it.sad.students.eventboard.service.httpbody.AuthenticationResponse;
import it.sad.students.eventboard.service.AuthenticationService;
import it.sad.students.eventboard.service.httpbody.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/noauth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){

        return service.register(request);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateRequest(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));

    }
}
