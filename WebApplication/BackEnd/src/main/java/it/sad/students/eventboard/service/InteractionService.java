package it.sad.students.eventboard.service;

import it.sad.students.eventboard.communication.EmailMessage;
import it.sad.students.eventboard.communication.EmailSenderService;
import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.*;
import it.sad.students.eventboard.service.custom.StatusCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InteractionService {

    private final AuthorizationService authorizationService;
    private final StatusCodes statusCodes;
    private final EmailSenderService emailSenderService;

    public ResponseEntity setLike(Long person,Long event,String token){
        try {
            if(!authorizationService.checkOwnerAuthorization(person,token))
                return statusCodes.unauthorized();

            if(DBManager.getInstance().getEventDao().findByPrimaryKey(event) == null)
                return statusCodes.notFound();

            Like like=DBManager.getInstance().getLikeDao().findByPrimaryKey(person,event);

            if(like == null)
                DBManager.getInstance().getLikeDao().saveOrUpdate(new Like(person,event,LocalDate.from(date())));
            else
                DBManager.getInstance().getLikeDao().delete(like);

            return statusCodes.ok();

        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.commandError();
        }
    }

    public ResponseEntity setPartecipation(Long person,Long event,String token){
        try {
            if(!authorizationService.checkOwnerAuthorization(person,token))
                return statusCodes.unauthorized();

            if(DBManager.getInstance().getEventDao().findByPrimaryKey(event) == null)
                return statusCodes.notFound();

            Partecipation partecipation = DBManager.getInstance().getPartecipationDao().findByPrimaryKey(person, event);

            if(partecipation == null)
                DBManager.getInstance().getPartecipationDao().saveOrUpdate(new Partecipation(LocalDate.from(date()),person,event));
            else
                DBManager.getInstance().getPartecipationDao().delete(partecipation);

            return statusCodes.ok();

        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.commandError();
        }
    }

    public ResponseEntity addComment(Comment comment, String token){
        try{

            if(!authorizationService.checkOwnerAuthorization(comment.getPerson(),token))
                return statusCodes.unauthorized();
            if(DBManager.getInstance().getEventDao().findByPrimaryKey(comment.getEvent()) == null)
                return statusCodes.notFound();

            comment.setDate(date());
            DBManager.getInstance().getCommentDao().saveOrUpdate(comment);

            Long organizer=DBManager.getInstance().getEventDao().findByPrimaryKey(comment.getEvent()).getOrganizer();
            if(organizer!=comment.getPerson()){ //se chi ha fatto il commento è diverso da chi ha creato il post dell'evento

                Person person = DBManager.getInstance().getPersonDao().findByPrimaryKey(organizer);
                Event event=DBManager.getInstance().getEventDao().findByPrimaryKey(comment.getEvent());
                emailSenderService.sendEmail(newMessageAdd(person,true,event.getTitle()));
            }

             return statusCodes.ok();
        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.commandError();
        }
    }

    public ResponseEntity addReview(Review review,String token){
        try {
            //      PRIMO METODO
            if(!authorizationService.checkOwnerAuthorization(review.getPerson(),token))
                return statusCodes.unauthorized();
            if(DBManager.getInstance().getEventDao().findByPrimaryKey(review.getEvent()) == null)
                return statusCodes.notFound();

            if(DBManager.getInstance().getReviewDao().findByPrimaryKey(review.getPerson(),review.getEvent()) == null){
                review.setDate(date());
                DBManager.getInstance().getReviewDao().saveOrUpdate(review);
                Long organizer=DBManager.getInstance().getEventDao().findByPrimaryKey(review.getEvent()).getOrganizer();
                if(organizer!=review.getPerson()){ //se chi ha fatto il commento è diverso da chi ha creato il post dell'evento

                    Person person = DBManager.getInstance().getPersonDao().findByPrimaryKey(organizer);
                    Event event=DBManager.getInstance().getEventDao().findByPrimaryKey(review.getEvent());
                    emailSenderService.sendEmail(newMessageAdd(person,false,event.getTitle()));
                }
                return statusCodes.ok();
            }
            return statusCodes.notFound();

        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.commandError();
        }
    }

    public ResponseEntity deleteComment(Long id,String token,String message){
        try {
            Comment comment=DBManager.getInstance().getCommentDao().findByPrimaryKey(id);
            if(comment == null)
                return statusCodes.notFound();

            if(!authorizationService.checkOwnerOrAdminAuthorization(comment.getPerson(), token))
                return statusCodes.unauthorized();

            DBManager.getInstance().getCommentDao().delete(comment);
            if(authorizationService.checkAdminAuthorization(token)){
                Event event=DBManager.getInstance().getEventDao().findByPrimaryKey(comment.getEvent());
                String email = DBManager.getInstance().getPersonDao().findByPrimaryKey(comment.getPerson()).getEmail();
                emailSenderService.sendEmail(newMessageDeleteOrUpdate(email,true,true,event.getTitle(), message));
            }
            return statusCodes.ok();
        }catch (Exception e){
            e.printStackTrace();
            return  statusCodes.commandError();
        }
    }

    public ResponseEntity deleteReview(Long person,Long event,String token,String message){
        try {
            if(!authorizationService.checkOwnerOrAdminAuthorization(person, token))
                return statusCodes.unauthorized();

            Review review=DBManager.getInstance().getReviewDao().findByPrimaryKey(person, event);

            if(review == null)
                return statusCodes.notFound();
            DBManager.getInstance().getReviewDao().delete(review);
            if(authorizationService.checkAdminAuthorization(token)){
                Event e=DBManager.getInstance().getEventDao().findByPrimaryKey(event);
                String email = DBManager.getInstance().getPersonDao().findByPrimaryKey(person).getEmail();
                emailSenderService.sendEmail(newMessageDeleteOrUpdate(email,false,true,e.getTitle(), message));
            }
            return statusCodes.ok();

        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.commandError();
        }
    }


    public ResponseEntity updateComment(Comment comment, String token,String message) {
        try{
            if(comment == null)
                return statusCodes.notFound();

            if(!authorizationService.checkOwnerOrAdminAuthorization(comment.getPerson(), token))
                return statusCodes.unauthorized();
            else
            {
                comment.setDate(date());
                if(DBManager.getInstance().getCommentDao().saveOrUpdate(comment)) {
                    if(authorizationService.checkAdminAuthorization(token)){

                        Event e=DBManager.getInstance().getEventDao().findByPrimaryKey(comment.getEvent());
                        String email = DBManager.getInstance().getPersonDao().findByPrimaryKey(comment.getPerson()).getEmail();
                        emailSenderService.sendEmail(newMessageDeleteOrUpdate(email,true,false,e.getTitle(), message));
                    }
                    return statusCodes.ok();
                }
                else
                    return statusCodes.commandError();
            }
        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.commandError();
        }
    }


    public ResponseEntity updateReview(Review review, String token,String message) {
        try {
            if(review == null)
                return statusCodes.notFound();

            if(!authorizationService.checkOwnerOrAdminAuthorization(review.getPerson(), token))
                return statusCodes.unauthorized();

            if(review.getRating() == null || review.getRating()<=0  || review.getRating()>10 ||
                    review.getMessage() == null || review.getMessage().equals(""))
                return statusCodes.commandError();

            review.setDate(date());
            if(DBManager.getInstance().getReviewDao().saveOrUpdate(review)) {
                if(authorizationService.checkAdminAuthorization(token)){
                    Event e=DBManager.getInstance().getEventDao().findByPrimaryKey(review.getEvent());
                    String email = DBManager.getInstance().getPersonDao().findByPrimaryKey(review.getPerson()).getEmail();
                    emailSenderService.sendEmail(newMessageDeleteOrUpdate(email,true,false,e.getTitle(), message));
                }
                return statusCodes.ok();
            }
            else
                return statusCodes.commandError();
        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.commandError();
        }

    }

    public ResponseEntity getReview(Long person,Long event){
        try {
            if (person == null || event == null )
                return statusCodes.commandError();
            Review review = DBManager.getInstance().getReviewDao().findByPrimaryKey(person,event);
            if (review == null)
                return  statusCodes.notFound();
            else
                return statusCodes.okGetElement(review);
        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.commandError();
        }
    }

    public ResponseEntity<Comment> getComment(Long id) {
        try{
            if (id == null )
                return statusCodes.commandError();
            Comment comment = DBManager.getInstance().getCommentDao().findByPrimaryKey(id);
            if (comment == null)
                return  statusCodes.notFound();
            else
                return ResponseEntity.ok(comment);
        }catch (Exception e){
            e.printStackTrace();
            return statusCodes.commandError();
        }

    }


    //extra functions
    private LocalDateTime date(){
        LocalDateTime date = LocalDateTime.now();
        return date;
    }


    private EmailMessage newMessageAdd(Person person,Boolean type,String title){ // type: true(comment) false(review)
        String object="";
        String message="";
        if(type){
            object="Hai ricevuto un commento ad un tuo Evento";
            message="L'utente "+person.getName()+" "+person.getLastName()+" ha commentato il tuo evento '"+title+"'";
        }else{
            object="Hai ricevuto una recensione ad un tuo Evento";
            message="L'utente "+person.getName()+" "+person.getLastName()+" ha recensito il tuo evento '"+title+"'";
        }

        return new EmailMessage(
                person.getEmail(),
                object,
                message);
    }

    private EmailMessage newMessageDeleteOrUpdate(String to,Boolean type,Boolean deleteOrUpdate,String title,String message){ //type: true(comment) false(review)
        String object="";                                                                                                     //delete: true, update: false
        if(deleteOrUpdate){
            if(type)
                object="L'admin ha eliminato il tuo commento all'evento '"+title+"'";
            else
                object="L'admin ha eliminato la tua recensione all'evento '"+title+"'";
        }else{
            if(type)
                object="L'admin ha modificato il tuo commento all'evento '"+title+"'";
            else
                object="L'admin ha modificato la tua recensione all'evento '"+title+"'";

        }
        return new EmailMessage(to,object,message);
    }

}
