package it.sad.students.eventboard.service;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.*;
import it.sad.students.eventboard.security.auth.AuthorizationControll;
import it.sad.students.eventboard.service.httpbody.StatusCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InteractionService {

    private final AuthorizationControll authorizationControll;
    private final StatusCodes statusCodes;

    // TODO: 06/01/2023 Settare eventuali error, Exception è generale??


    public ResponseEntity setLike(Long person,Long event,String token){
        try {
            if(!authorizationControll.checkOwnerAuthorization(person,token))
                return statusCodes.unauthorized();

            if(DBManager.getInstance().getEventDao().findByPrimaryKey(event)==null)
                return statusCodes.notFound();

            Like like=DBManager.getInstance().getLikeDao().findByPrimaryKey(person,event);

            if(like==null)
                DBManager.getInstance().getLikeDao().saveOrUpdate(new Like(person,event,LocalDate.from(date())));
            else
                DBManager.getInstance().getLikeDao().delete(like);

            return statusCodes.ok();

        }catch (Exception e){
            return statusCodes.notFound();
        }
    }

    public ResponseEntity setPartecipation(Long person,Long event,String token){
        try {
            if(!authorizationControll.checkOwnerAuthorization(person,token))
                return statusCodes.unauthorized();

            if(DBManager.getInstance().getEventDao().findByPrimaryKey(event)==null)
                return statusCodes.notFound();

            Partecipation partecipation = DBManager.getInstance().getPartecipationDao().findByPrimaryKey(person, event);

            if(partecipation==null)
                DBManager.getInstance().getPartecipationDao().saveOrUpdate(new Partecipation(LocalDate.from(date()),person,event));
            else
                DBManager.getInstance().getPartecipationDao().delete(partecipation);

            return statusCodes.ok();

        }catch (Exception e){
            return statusCodes.notFound();
        }
    }

    public ResponseEntity addComment(Comment comment, String token){
        try{

            if(!authorizationControll.checkOwnerAuthorization(comment.getPerson(),token))
                return statusCodes.unauthorized();
            if(DBManager.getInstance().getEventDao().findByPrimaryKey(comment.getEvent())==null)
                return statusCodes.notFound();

            comment.setDate(LocalDate.from(date()));
            DBManager.getInstance().getCommentDao().saveOrUpdate(comment);

             return statusCodes.ok();
        }catch (Exception e){
            return statusCodes.notFound();
        }
    }

    public ResponseEntity addReview(Review review,String token){
        try {
            //      PRIMO METODO
            if(!authorizationControll.checkOwnerAuthorization(review.getPerson(),token))
                return statusCodes.unauthorized();
            if(DBManager.getInstance().getEventDao().findByPrimaryKey(review.getEvent())==null)
                return statusCodes.notFound();

            if(DBManager.getInstance().getReviewDao().findByPrimaryKey(review.getPerson(),review.getEvent())==null){
                review.setDate(LocalDate.from(date()));
                DBManager.getInstance().getReviewDao().saveOrUpdate(review);
                return statusCodes.ok();
            }
            return statusCodes.notFound();

            /*
            //      SECONDO METODO: si puo gestire nel try cath del daoReview, se è gia esistente invia una eccezione
            DBManager.getInstance().getReviewDao().saveOrUpdate(review);
            return true;
            */
        }catch (Exception e){
            return statusCodes.notFound();
        }
    }

    public ResponseEntity deleteComment(Long id,String token){
        try {
            Comment comment=DBManager.getInstance().getCommentDao().findByPrimaryKey(id);
            if(comment==null)
                return statusCodes.notFound();

            if(!authorizationControll.checkOwnerOrAdminAuthorization(comment.getPerson(), token))
                return statusCodes.unauthorized();

            DBManager.getInstance().getCommentDao().delete(comment);
            // TODO: 06/01/2023 valutare eliminazione solo con chiave e non passando tutto il commento
            return statusCodes.ok();
        }catch (Exception e){
            e.printStackTrace();
            return  statusCodes.notFound();
        }
    }

    public ResponseEntity deleteReview(Long person,Long event,String token){
        try {
            if(!authorizationControll.checkOwnerOrAdminAuthorization(person, token))
                return statusCodes.unauthorized();

            //si potrebbe gestire come scritto sopra nel secondo metodo addReview
            Review review=DBManager.getInstance().getReviewDao().findByPrimaryKey(person, event);

            if(review==null)
                return statusCodes.notFound();
            DBManager.getInstance().getReviewDao().delete(review);
            // TODO: 06/01/2023 valutare eliminazione solo con chiavi e non passando tutta la review
            return statusCodes.ok();

        }catch (Exception e){
            //e.printStackTrace();
            return statusCodes.notFound();
        }
    }


    public ResponseEntity updateComment(Comment comment, String token) {

        if(comment==null)
            return statusCodes.notFound();

        if(!authorizationControll.checkOwnerOrAdminAuthorization(comment.getPerson(), token))
            return statusCodes.unauthorized();
        else
        {
            if(DBManager.getInstance().getCommentDao().saveOrUpdate(comment))
                return statusCodes.ok();
            else
                return statusCodes.commandError();
        }


    }


    public ResponseEntity updateReview(Review review, String token) {
        try {
            if(review==null)
                return statusCodes.notFound();

            if(!authorizationControll.checkOwnerOrAdminAuthorization(review.getPerson(), token))
                return statusCodes.unauthorized();

            if(review.getRating()==null || review.getRating()<=0||review.getRating()>10||
                    review.getMessage()==null ||review.getMessage()=="")
                return statusCodes.commandError();
            System.out.println("ok");
            // TODO: 09/01/2023 modificare la data??
            //review.setDate(LocalDate.from(date()));
            if(DBManager.getInstance().getReviewDao().saveOrUpdate(review))
                return statusCodes.ok();
            else
                return statusCodes.commandError();
        }catch (Exception e){
            return statusCodes.notFound();
        }

    }

    public ResponseEntity getReview(Long person,Long event){
        try {
            if (person==null||event==null )
                return statusCodes.commandError();
            Review review = DBManager.getInstance().getReviewDao().findByPrimaryKey(person,event);
            if (review==null)
                return  statusCodes.notFound();
            else
                return statusCodes.okGetElement(review);
        }catch (Exception e){
            return statusCodes.notFound();
        }
    }








    //extra functions
    public LocalDateTime date(){
        LocalDateTime date = LocalDateTime.now();
        return date;
    }



    public ResponseEntity<Comment> getComment(Long id) {
        if (id==null )
            return statusCodes.commandError();
        Comment comment = DBManager.getInstance().getCommentDao().findByPrimaryKey(id);
        if (comment==null)
            return  statusCodes.notFound();
        else
            return ResponseEntity.ok(comment);
    }

    // TODO: 06/01/2023 IL comando extractUsername da errore "  Illegal base64url character: ' '    "
    //  (se metti il e.printStackTrace() nel metodo che richiama questo metodo lo noti)
    /*
    public boolean checkUserAndAdmin(Long id,String token){
        System.out.println(token);
        String jwt = token.substring(7);
        //String jwt = token.split(" ")[1].trim();

        System.out.println("|"+jwt+"|");
        Person person=DBManager.getInstance().getPersonDao().findByUsername(jwtService.extractUsername(jwt));
        if(person==null) return false;
        System.out.println(person.getId());
        System.out.println(person.getId().equals(id));
        System.out.println(person.getRole().toString().equals("ADMIN"));
        return (person.getId().equals(id) || person.getRole().toString().equals("ADMIN"));
    }


    public boolean checkUser(Long id,String token){
        String jwt = token.substring(7);
        Person person=DBManager.getInstance().getPersonDao().findByUsername(jwtService.extractUsername(jwt));
        if(person==null) return false;
        return person.getId().equals(id);
    }
    */

}
