package it.sad.students.eventboard.controller.api;

import it.sad.students.eventboard.persistenza.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
public class RestApiController {
    /* si utilizza la seguente annotazione per far innietare a spring, la dipendenza
    del service nel controller
    @Autowired
    private PersonService personService;

    mentre nel service, prima della dichiarazione della classe scriviamo
    @Service

    ricordarsi che Service, Repository e Controller, fan parte tutti dell'annotation Component
    il seguente metodo non lo usiamo
    @qualifier (mainservice) e prima della classe usare @Service("mainservice")
    https://www.youtube.com/watch?v=vrwitaK2KQk&ab_channel=LessTheoryAcademy

    */



    private List<Person> list;
    private Long lastId;

    public RestApiController(){
        list = new ArrayList<>();
        list.add(new Person(1L, "Pers 1", "Cogn 1", "username1", "password1", "email1", true, 1L,1L));
        list.add(new Person(2L, "Pers 2", "Cogn 2", "username2", "password2", "email2", true, 2L,2L));
        list.add(new Person(3L, "Pers 3", "Cogn 3", "username3", "password3", "email3", true, 3L,3L));

        lastId = 3L;
    }



    //restituire lista di cose o una cosa...funziona con il get
    @RequestMapping("/api/persons")
    public Iterable<Person> getAll(){


        return list;
    }

    @RequestMapping("/api/persons/{id}")//cerca persone con id
    public Person getById(@PathVariable Long id){
        Optional<Person> person = list.stream().filter(item->item.getId() == id).findFirst();

        if(person.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"persona non trovata");

        return person.get();
    }

    //creare una nuova persona dal front-end METODO POST
    @RequestMapping(value="/api/persons",method= RequestMethod.POST)
    public Person create(@RequestBody Person person){

        lastId++;
        person.setId(lastId);

        list.add(person);

        return  person;

    }

    //modificare una persona dal front-end METODO PUT
    @RequestMapping(value="/api/persons/{id}",method= RequestMethod.PUT)
    public Person update(@PathVariable Long id, @RequestBody Person person){

        Optional<Person> trovaPers = list.stream().filter(item->item.getId() == id).findFirst();

        if(trovaPers.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"persona non trovata");

        trovaPers.get().setName(person.getName());

        return  trovaPers.get();

    }

    //deletare una persona dal front-end METODO DELETE
    @RequestMapping(value="/api/persons/{id}",method= RequestMethod.DELETE)
    public void delete(@PathVariable Long id){

        Optional<Person> trovaPers = list.stream().filter(item->item.getId() == id).findFirst();

        if(trovaPers.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"persona non trovata");

        list.remove(trovaPers.get());

    }


}
