package it.sad.students.eventboard.controller.api;

import it.sad.students.eventboard.service.InteractionService;
import it.sad.students.eventboard.service.UserService;
import it.sad.students.eventboard.service.httpbody.RequestUserEdit;
import it.sad.students.eventboard.service.httpbody.RequestCancellation;
import it.sad.students.eventboard.service.httpbody.RequestMotivation;
import it.sad.students.eventboard.service.httpbody.ResponsePerson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final InteractionService interactionService;

   @RequestMapping("/api/user/{username}")
    public ResponseEntity<ResponsePerson> getPerson(@PathVariable String username){
        return userService.getPerson(username);
    }

    @RequestMapping("/api/user/admin/get/all")
    public ResponseEntity<Iterable<ResponsePerson>> getPersons(@RequestHeader (name="Authorization") String token){
       return userService.getPersons(token);
    }

    @RequestMapping(value="/api/user/update",method = RequestMethod.PUT)
    public ResponseEntity editUser(@RequestBody RequestUserEdit person, @RequestHeader (name="Authorization") String token){
        //utente non autorizzato 403`
        //utente non proprietario di quel account, campi sbagliati operazione non eseguibile codice 400`
        //utente nullo, errore elaborazione 404 not found`
        //utente modificato aggiunto codice 200 ok`
       return userService.updateUser(person,token);
    }

    // TODO: 08/01/2023 AGGIUNGERE SU TRELLO
    @RequestMapping("/api/noauth/activate/{token}")
    public ResponseEntity activateUser(@PathVariable String token){
       return userService.activateUser(token);
    }

    @RequestMapping("/api/noauth/retrieve/password/{username}")
    public ResponseEntity retrievePassword(@PathVariable String username){

       return userService.retrievePassword(username);
    }

    // TODO: 08/01/2023 AGGIUNGERE SU TRELLO
    @RequestMapping(value="/api/user/delete",method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestBody RequestCancellation requestCancellation, @RequestHeader (name="Authorization") String token){
        return userService.deleteUser(requestCancellation,token);

    }

    // TODO: 08/01/2023 AGGIUNGERE SU TRELLO
    @RequestMapping(value="/api/user/admin/set/ban/{id}",method = RequestMethod.POST)
    public ResponseEntity userBan(@RequestBody RequestMotivation requestMotivation, @PathVariable Long id, @RequestHeader (name="Authorization") String token){
        return userService.setUserBan(requestMotivation,id,token);
    }


    @RequestMapping(value="/api/noauth/organizer/{id}",method = RequestMethod.GET)
    public ResponseEntity getOrganizer(@PathVariable Long id){
        //utente non autorizzato 403
        //utente non è un Organizer 400
        //utente non trovato, errore nel sistema 404 not found
        //responde dei dati (nome,email e elenco eventi) 200 ok
        return userService.getOrganizer(id);
    }

    @RequestMapping(value="/api/user/admin/promote/{id}")
    public ResponseEntity promoteToAdmin(@PathVariable Long id, @RequestHeader (name="Authorization") String token){
        return userService.promoteToAdmin(id,token);
    }








}
