package it.sad.students.eventboard.service;

import it.sad.students.eventboard.communication.EmailMessage;
import it.sad.students.eventboard.communication.EmailSenderService;
import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.*;
import it.sad.students.eventboard.configsecurity.JwtService;
import it.sad.students.eventboard.service.httpbody.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final JwtService jwtService;
    private final AuthorizationControll authorizationControll;
    private final EmailSenderService emailSenderService;

    public ResponseEntity<Iterable<ResponseEvent>>  getAllEvents(){
        //errore 404 not found...lista vuota
        //codice 200 ok

        List<Event> tmp = DBManager.getInstance().getEventDao().findAll();
        if (tmp.isEmpty())
            return ResponseEntity.notFound().build();
        else{
            return ResponseEntity.ok(createList(tmp));
        }

    }

    public ResponseEntity<Iterable<ResponseEvent>> getPreferredEvents(List<EventType> eventTypes) {
        //errore 404 not found...lista tipi di eventi vuota e/o lista filtrata vuota
        //codice 200 ok

        List<Event> tmp = new ArrayList<>();
        for (EventType eventType: eventTypes){
            tmp.addAll(DBManager.getInstance().getEventDao().findByType(eventType));
        }
        if (tmp.isEmpty())
            return ResponseEntity.notFound().build();
        List<ResponseEvent> events = createList(tmp);
        if (events.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(events);
    }

    private List<ResponseEvent> createList(List<Event> tmp){
        ArrayList<ResponseEvent> events = new ArrayList<>();

        for (Event event: tmp){
            Person person = DBManager.getInstance().getPersonDao().findByPrimaryKey(event.getOrganizer());
            events.add(new ResponseEvent
                    (event.getId(),
                            event.getDate(),
                            event.getTitle(),
                            event.getUrlPoster(),
                            event.getEventType().toString(),
                            DBManager.getInstance().getPositionDao().findByPrimaryKey(event.getPosition()),(
                            person.getName()+
                                    " "+
                                    person.getLastName()
                    )
                    ));

        }
        return events;
    }

    public ResponseEntity<ResponseEventCreation> createEvent(RequestCreationEvent requestCreationEvent, String token) {
        if (requestCreationEvent.getEvent()==null)
            return ResponseEntity.notFound().build();

        //event.setDate(LocalDateTime.now());


        if(authorizationControll.checkOwnerAuthorization(requestCreationEvent.getEvent().getOrganizer(),token)){
            if (DBManager.getInstance().getPositionDao().saveOrUpdate(requestCreationEvent.getPosition())){
                requestCreationEvent.getEvent().setPosition(requestCreationEvent.getPosition().getId());
                if(DBManager.getInstance().getEventDao().saveOrUpdate(requestCreationEvent.getEvent()))
                    return ResponseEntity.ok(ResponseEventCreation.builder().id(requestCreationEvent.getEvent().getId()).build()) ;
                else
                    DBManager.getInstance().getPositionDao().delete(requestCreationEvent.getPosition());
            }
        }
        return ResponseEntity.badRequest().build();

    }

    public ResponseEntity deleteEvent (Long id, String token, String message){


        Event event = DBManager.getInstance().getEventDao().findByPrimaryKey(id);
        if (event==null)
            return ResponseEntity.notFound().build();


        if(authorizationControll.checkOwnerOrAdminAuthorization(event.getOrganizer(), token)){
            DBManager.getInstance().getEventDao().delete(event);
            if(authorizationControll.checkAdminAuthorization( token)){
                String email = DBManager.getInstance().getPersonDao().findByPrimaryKey(event.getOrganizer()).getEmail();
                emailSenderService.sendEmail(AdminActionEventNotification(email,
                        "Notifica rimozione evento "+event.getTitle(), message));
            }
            return ResponseEntity.ok("Rimozione effettuata");
        }
        else
            return ResponseEntity.badRequest().body("Non hai i permessi per rimuovere l'evento");
    }

    public ResponseEntity updateEvent(RequestCreationEvent requestCreationEvent,String message, String token) {
        if(requestCreationEvent.getEvent()==null)
            return ResponseEntity.notFound().build();
        if (authorizationControll.checkOwnerOrAdminAuthorization(requestCreationEvent.getEvent().getOrganizer(),token)){
            if (DBManager.getInstance().getPositionDao().saveOrUpdate(requestCreationEvent.getPosition())){
                requestCreationEvent.getEvent().setPosition(requestCreationEvent.getPosition().getId());
                if(DBManager.getInstance().getEventDao().saveOrUpdate(requestCreationEvent.getEvent())) {
                    if (authorizationControll.checkAdminAuthorization(token)) {
                        String email = DBManager.getInstance().getPersonDao().findByPrimaryKey(requestCreationEvent.getEvent().getOrganizer()).getEmail();
                        emailSenderService.sendEmail(AdminActionEventNotification(email,
                                "Notifica modifica evento " + requestCreationEvent.getEvent().getTitle(), message));
                    }
                    return ResponseEntity.ok("Evento modificato");
                }
                else
                    DBManager.getInstance().getPositionDao().delete(requestCreationEvent.getPosition());
            }


        }
        return ResponseEntity.badRequest().body("Non hai i permessi per modificare l'evento");
    }


    public ResponseEntity<ResponseEventDetails> getEventFullDetails(Long id) {
        //errore 404 not found...evento non trovato
        //codice 200 ok
        Event event = DBManager.getInstance().getEventDao().findByPrimaryKey(id);
        if (event==null)
            return ResponseEntity.notFound().build();
        List<Comment> comments = DBManager.getInstance().getCommentDao().findByEvent(id);
        List<ResponseComment> commentList = new ArrayList<>();
        for (Comment comment:comments){
            Person person = DBManager.getInstance().getPersonDao().findByPrimaryKey(comment.getPerson());
            commentList.add(new ResponseComment(
                    comment.getId(),
                    comment.getDate(),
                    comment.getMessage(),
                    comment.getEvent(),
                    comment.getPerson(),
                    (person.getName()+" "+person.getLastName())));
        }


        List<Like> likeList = DBManager.getInstance().getLikeDao().findByEvent(id);
        List<Partecipation> partecipationList = DBManager.getInstance().getPartecipationDao().findByEvent(id);

        List<Review> reviews =  DBManager.getInstance().getReviewDao().findByEvent(id);
        List<ResponseReview> reviewList = new ArrayList<>();
        for (Review review:reviews){
            Person person = DBManager.getInstance().getPersonDao().findByPrimaryKey(review.getPerson());
            reviewList.add(new ResponseReview(
                    review.getDate(),
                    review.getMessage(),
                    review.getRating(),
                    review.getPerson(),
                    review.getEvent(),
                    (person.getName()+" "+person.getLastName())));
        }



        Person person = DBManager.getInstance().getPersonDao().findByPrimaryKey(event.getOrganizer());
        String organizerFullName =person.getName() + " "+person.getLastName();
        Position position = DBManager.getInstance().getPositionDao().findByPrimaryKey(event.getPosition());

        return ResponseEntity.ok(new ResponseEventDetails(event,organizerFullName,position ,commentList,likeList,partecipationList,reviewList));

    }


    public ResponseEntity<EventType[]> getEventType(){
        return ResponseEntity.ok(EventType.values());
    }

    private EmailMessage AdminActionEventNotification(String email, String subject, String message){

        return new EmailMessage(email,subject,message);
    }
}
