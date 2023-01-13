package it.sad.students.eventboard.service;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Person;
import it.sad.students.eventboard.configsecurity.JwtService;
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

    //id della persona
    public boolean checkOwnerAuthorization(Long id,String token){
        String jwt = token.substring(7);
        Person person= DBManager.getInstance().getPersonDao().findByUsername(jwtService.extractUsername(jwt));
        if(person==null) return false;
        return person.getId().equals(id);
    }


    /*
    public boolean checkSpecificAdminAuthorization(Long id,String token){
        String jwt = token.substring(7);
        Person person= DBManager.getInstance().getPersonDao().findByUsername(jwtService.extractUsername(jwt));
        if(person==null) return false;
        return (person.getId().equals(id) && person.getRole().toString().equals("ADMIN"));
    }
     */

    public boolean checkAdminAuthorization(String token){
        String jwt = token.substring(7);
        Person person= DBManager.getInstance().getPersonDao().findByUsername(jwtService.extractUsername(jwt));
        if(person==null) return false;
        return person.getRole().toString().equals("ADMIN");
    }

    public String extractUsername(String token){
        //String jwt = token.substring(7);
        return jwtService.extractUsername(token);
    }

}
