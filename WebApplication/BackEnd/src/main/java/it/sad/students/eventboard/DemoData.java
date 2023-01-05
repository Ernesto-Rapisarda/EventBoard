package it.sad.students.eventboard;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class DemoData {

    public DemoData(){
        /*
        Position position=createPosition();
        DBManager.getInstance().getPositionDao().saveOrUpdate(position);

        EventType eventType=createEventType("azione","Nu burdell");
        DBManager.getInstance().getEventTypeDao().saveOrUpdate(eventType);
        */
        //System.out.println(DBManager.getInstance().getPositionDao().findAll().get(0).getId());

        /*
        Person person1=createPerson("Andrea","Tocci","YocoPocoMaYoco",
                "andrea@tcc",true, 4L, Role.ADMIN,"password");
        DBManager.getInstance().getPersonDao().saveOrUpdate(person1);

        Person person2=createPerson("Simone","Rotundo","Catanzarese",
                "simo@tcc",true, 5L, Role.USER,"password");
        DBManager.getInstance().getPersonDao().saveOrUpdate(person2);


        Person person3=createPerson("Alessandro","Monetti","Pingu",
                "pingu@tcc",true, 4L, Role.PUBLISHER,"password");
        DBManager.getInstance().getPersonDao().saveOrUpdate(person3);


        Person person4=createPerson("Ernesto","Rapisarda","Nocera",
                "erne@tcc",true, 5L, Role.PUBLISHER,"password");
        DBManager.getInstance().getPersonDao().saveOrUpdate(person4);
         */

        LocalDateTime date = LocalDateTime.now();
        date = date.minusSeconds(ThreadLocalRandom.current().nextInt(1, 30326400));


        Event event1 =createEvent(4L, LocalDate.from(date), 2L,10.5,
                "nulla piu totale",false, "Evento incredibile",
                DBManager.getInstance().getPersonDao().findByUsername("Pingu").getId());
        DBManager.getInstance().getEventDao().saveOrUpdate(event1);

        Event event2 =createEvent(5L,LocalDate.from(date), 1L,10.5,
                "easy easy",false, "Evento incredibile",
                DBManager.getInstance().getPersonDao().findByUsername("Nocera").getId());
        DBManager.getInstance().getEventDao().saveOrUpdate(event2);



        /*

         */

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
        event.setPublisher(publisher);
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
