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
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.table.TableRowSorter;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailSenderService emailSenderService;
    private final StatusCodes statusCodes;


    public ResponseEntity register (RegisterRequest request) {
      try {
        var user = new Person();
        user.setId(null);
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setEnabled(false);
        user.setPosition(1L);
        user.setRole(request.getRole());
        user.setIs_not_locked(true);

        if(!DBManager.getInstance().getPersonDao().saveOrUpdate(user))
          return statusCodes.commandError();
        var jwtToken = jwtService.generateToken(user);

        if(!sendEmail(request.getEmail(), request.getName(), request.getLastName(), jwtToken)) {
          DBManager.getInstance().getPersonDao().delete(user);
          return statusCodes.commandError();
        }

        /*
        return statusCodes.okGetElement(AuthenticationResponse.builder()
                .token(jwtToken)
                .build()) ;*/

        return statusCodes.ok();
      } catch(Exception e) {
        e.printStackTrace();
        return statusCodes.commandError();
      }

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
                 .build() ;
    }

    private boolean sendEmail(String email,String name, String lastname,String token){
        try {
            EmailMessage emailMessage = new EmailMessage();
            emailMessage.setTo(email);
            emailMessage.setSubject("Conferma registrazione");
            emailMessage.setMessage("Benvenuto "+
                    name+" "+
                    lastname+", \n"+
                    "ti confermiamo che la tua registrazione è stata completata con successo.\n"+
                    "Per poter accedere ai servizi, devi confermare il tuo indirizzo email, cliccando sul link seguente:\n"+
                    htmlActivation(token)
                    +"\n\nGrazie per esserti registrato con noi. Siamo lieti di averti come nuovo utente e non vediamo l'ora di aiutarti a raggiungere i tuoi obiettivi.\n" +
                    "\n" +
                    "Saluti,\n" +
                    "GoodVibes"

            );
            if(emailSenderService.sendEmail(emailMessage))
                return true;
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private String htmlActivation(String token){
        return "<a title=\"ATTIVA L'ACCOUNT\" href=\"http://localhost:4200/profile/activate/"+token+"\">ATTIVA L'ACCOUNT</a>";
        //http://localhost:4200/activate/token
    }

}

