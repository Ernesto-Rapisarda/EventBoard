package it.sad.students.eventboard;

import it.sad.students.eventboard.persistenza.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class DemoData {

    public DemoData(){

        /*
                    POSITION
        Position position=createPosition();
        DBManager.getInstance().getPositionDao().saveOrUpdate(position);
        */


        /*         //   EVENT_TYPE
        EventType eventType2= createEventType("porno","easy peasy");
        DBManager.getInstance().getEventTypeDao().saveOrUpdate(eventType2);
        EventType eventType=createEventType("azione","Nu burdell");
        DBManager.getInstance().getEventTypeDao().saveOrUpdate(eventType);

*/

        //System.out.println(DBManager.getInstance().getPositionDao().findAll().get(0).getId());

        /*
                    PERSON
        Person person1=createPerson("Andrea","Tocci","YocoPocoMaYoco",
                "andrea@tcc",true, 1L, Role.ADMIN,"password");
        DBManager.getInstance().getPersonDao().saveOrUpdate(person1);

        Person person2=createPerson("Simone","Rotundo","Catanzarese",
                "simo@tcc",true, 2L, Role.USER,"password");
        DBManager.getInstance().getPersonDao().saveOrUpdate(person2);

        Person person3=createPerson("Alessandro","Monetti","Pingu",
                "pingu@tcc",true, 1L, Role.PUBLISHER,"password");
        DBManager.getInstance().getPersonDao().saveOrUpdate(person3);

        Person person4=createPerson("Ernesto","Rapisarda","Nocera",
                "erne@tcc",true, 2L, Role.PUBLISHER,"password");
        DBManager.getInstance().getPersonDao().saveOrUpdate(person4);
        */




  /*                 // EVENT
        Event event1 =createEvent(1L, LocalDate.from(dateTime()), 2L,10.5,
                "nulla piu totale",false, "Evento incredibile",
                DBManager.getInstance().getPersonDao().findByUsername("Pingu").getId());
        DBManager.getInstance().getEventDao().saveOrUpdate(event1);

        Event event2 =createEvent(2L,LocalDate.from(dateTime()), 1L,10.5,
                "easy easy",false, "Evento incredibile",
                DBManager.getInstance().getPersonDao().findByUsername("Nocera").getId());
        DBManager.getInstance().getEventDao().saveOrUpdate(event2);
*/


/*
                  // LIKE
        Like like1=createLike(28L,5L,LocalDate.from(dateTime()));
        DBManager.getInstance().getLikeDao().saveOrUpdate(like1);
        Like like2=createLike(28L,6L,LocalDate.from(dateTime()));
        DBManager.getInstance().getLikeDao().saveOrUpdate(like2);
        Like like3=createLike(29L,5L,LocalDate.from(dateTime()));
        DBManager.getInstance().getLikeDao().saveOrUpdate(like3);
        Like like4=createLike(32L,7L,LocalDate.from(dateTime()));
        DBManager.getInstance().getLikeDao().saveOrUpdate(like4);

*/



    }

    public LocalDateTime dateTime(){ //data casuale
        LocalDateTime date = LocalDateTime.now();
        date = date.minusSeconds(ThreadLocalRandom.current().nextInt(1, 30326400));
        return date;
    }

    public Position createPosition(){
        Position position=new Position();
        return position;
    }

    public Person createPerson(
            String name
            ,String lastname
            ,String username
            ,String email
            ,Boolean active_status
            ,Long position
            ,Role role
            ,String password
    ){
        Person person=new Person();
        person.setName(name);
        person.setLastName(lastname);
        person.setUsername(username);
        person.setEmail(email);
        person.setActiveStatus(active_status);
        person.setRole(role);
        person.setPosition(position);
        person.setPassword(password);
        return person;
    }

    public EventType createEventType(String name,String description){
        EventType eventType=new EventType();
        eventType.setName(name);
        eventType.setDescription(description);
        return eventType;
    }
    public Event createEvent(Long position
            ,LocalDate date
            ,Long event_type
            ,Double price
            ,String poster
            ,Boolean soldout
            ,String description
            ,Long publisher)
    {
        Event event=new Event();
        event.setPosition(position);
        event.setDate(date);
        event.setEventType(event_type);
        event.setPrice(price);
        event.setUrlPoster(poster);
        event.setSoldOut(soldout);
        event.setDescription(description);
        event.setOrganizer(publisher);
        return event;
    }
    public Like createLike(
            Long person
            ,Long event
            ,LocalDate date)
    {
        Like like=new Like();
        like.setDate(date);
        like.setPerson(person);
        like.setIdEvent(event);
        return like;
    }
}
