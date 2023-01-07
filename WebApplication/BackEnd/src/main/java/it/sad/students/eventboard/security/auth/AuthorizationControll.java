package it.sad.students.eventboard.security.auth;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Person;
import it.sad.students.eventboard.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationControll {
    private final JwtService jwtService;

    //id della persona associata all'evento,commento,ecc
    public boolean checkOwnerOrAdminAuthorization(Long id,String token){
        String jwt = token.substring(7);
        Person person= DBManager.getInstance().getPersonDao().findByUsername(jwtService.extractUsername(jwt));
        if(person==null) return false;
        return (person.getId().equals(id) || person.getRole().toString().equals("ADMIN"));
    }
}
