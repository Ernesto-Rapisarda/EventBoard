package it.sad.students.eventboard.controller.api;

import it.sad.students.eventboard.service.custom.request.RequestAuthentication;
import it.sad.students.eventboard.service.custom.response.ResponseAuthentication;
import it.sad.students.eventboard.service.AuthenticationService;
import it.sad.students.eventboard.service.custom.request.RequestRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://0.0.0.0:4200")
@RequestMapping("/api/noauth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity register(
            @RequestBody RequestRegister request
    ){

        return service.register(request);
    }

    @PostMapping("/authenticate")
    public ResponseAuthentication authenticateRequest(
            @RequestBody RequestAuthentication request
    ){
        return service.authenticate(request);

    }
}
