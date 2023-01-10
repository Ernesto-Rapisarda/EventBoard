package it.sad.students.eventboard.service;


import it.sad.students.eventboard.communication.EmailMessage;
import it.sad.students.eventboard.communication.EmailSenderService;
import it.sad.students.eventboard.service.httpbody.AuthenticationRequest;
import it.sad.students.eventboard.service.httpbody.AuthenticationResponse;
import it.sad.students.eventboard.configsecurity.JwtService;
import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Person;
import it.sad.students.eventboard.service.httpbody.RegisterRequest;
import it.sad.students.eventboard.service.httpbody.StatusCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailSenderService emailSenderService;
    private final StatusCodes statusCodes;


    public ResponseEntity<AuthenticationResponse> register (RegisterRequest request) {
        var user = new Person(null,
                request.getName(),
                request.getLastName(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                request.getActiveStatus(), 1L, request.getRole());
        if(!DBManager.getInstance().getPersonDao().saveOrUpdate(user))
            return statusCodes.commandError();
        var jwtToken = jwtService.generateToken(user);

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setTo(request.getEmail());
        emailMessage.setSubject("Conferma avvenuta registrazione");
        emailMessage.setMessage("Benvenuto "+
                request.getName()+" "+
                request.getLastName()+", \n"+
                "la tua registrazione è andata a buon fine.\n"+
                "Da adesso in poi, potrai navigare sul sito sfruttando tutte le sue funzionalità."

        );
        emailSenderService.sendEmail(emailMessage);


        return statusCodes.okGetElement(AuthenticationResponse.builder()
                .token(jwtToken)
                .build()) ;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = DBManager.getInstance().getPersonDao().findByUsername(request.getUsername());

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
