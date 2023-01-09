package it.sad.students.eventboard.controller.api;

import it.sad.students.eventboard.persistenza.model.Comment;
import it.sad.students.eventboard.persistenza.model.Person;
import it.sad.students.eventboard.persistenza.model.Review;
import it.sad.students.eventboard.service.InteractionService;
import it.sad.students.eventboard.service.RequestPersonEvent;
import it.sad.students.eventboard.service.UserService;
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
    public ResponseEntity<Person> getPerson(@PathVariable String username){
        return ResponseEntity.ok(userService.getPerson(username));
    }

    // TODO: 06/01/2023 Revisionare path scritti

    @RequestMapping(value="api/user/edit",method = RequestMethod.PUT)
    public ResponseEntity editUser(@RequestBody Person person,@RequestHeader (name="Authorization") String token){
        //utente non autorizzato 403`
        //utente non proprietario di quel account, campi sbagliati operazione non eseguibile codice 400`
        //utente nullo, errore elaborazione 404 not found`
        //utente modificato aggiunto codice 200 ok`
       return userService.editUser(person,token);
    }

    // TODO: 08/01/2023 Decidere se fare rimozione a cascata dal database
    @RequestMapping(value="api/user/delete",method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestBody Long id,@RequestHeader (name="Authorization") String token){
        return userService.disableUser(id,token);
        //id lo passi dal path
        //nel body, deve mettere nel body username e password, admin mette tutto null
    }
    @RequestMapping(value="api/user/enable",method = RequestMethod.POST)
    public ResponseEntity enableUser(@RequestBody Long id,@RequestHeader (name="Authorization") String token){
        return userService.enableUser(id,token);
    }









}
