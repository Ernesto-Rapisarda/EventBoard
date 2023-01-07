package it.sad.students.eventboard.service;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.*;
import it.sad.students.eventboard.security.auth.AuthorizationControll;
import it.sad.students.eventboard.security.config.JwtService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class InteractionService {

    //private final JwtService jwtService;
    private final AuthorizationControll authorizationControll;

    // TODO: 06/01/2023 Settare eventuali error, Exception è generale??


    public Boolean setLike(Long person,Long event){
        try {
            Like like=DBManager.getInstance().getLikeDao().findByPrimaryKey(person,event);
            if(like==null)
                DBManager.getInstance().getLikeDao().saveOrUpdate(new Like(person,event,LocalDate.from(date())));
            else
                DBManager.getInstance().getLikeDao().delete(like);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Boolean setPartecipation(Long person,Long event){
        try {
            Partecipation partecipation = DBManager.getInstance().getPartecipationDao().findByPrimaryKey(person, event);
            if(partecipation==null)
                DBManager.getInstance().getPartecipationDao().saveOrUpdate(new Partecipation(LocalDate.from(date()),person,event));
            else
                DBManager.getInstance().getPartecipationDao().delete(partecipation);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ResponseEntity addComment(Comment comment, String token){
        try{
            /*
            if(!authorizationControll.checkOwnerAuthorization(comment.getPerson(),token))
                return ResponseEntity.badRequest().body("Error not authorize");

            comment.setDate(LocalDate.from(date()));
            DBManager.getInstance().getCommentDao().saveOrUpdate(comment);
            */
             return ResponseEntity.ok().body("ok");
        }catch (Exception e){
            return ResponseEntity.ok().body("ok");
        }
    }

    public Boolean addReview(Review review){
        try {
            //      PRIMO METODO
            if(DBManager.getInstance().getReviewDao().findByPrimaryKey(review.getPerson(),review.getEvent())==null){
                review.setDate(LocalDate.from(date()));
                DBManager.getInstance().getReviewDao().saveOrUpdate(review);
                return true;
            }
            return false;

            /*
            //      SECONDO METODO: si puo gestire nel try cath del daoReview, se è gia esistente invia una eccezione
            DBManager.getInstance().getReviewDao().saveOrUpdate(review);
            return true;
            */
        }catch (Exception e){
            return false;
        }
    }

    public Boolean deleteComment(Long id,String token){
        try {
            Comment comment=DBManager.getInstance().getCommentDao().findByPrimaryKey(id);

            if(!authorizationControll.checkOwnerOrAdminAuthorization(comment.getPerson(), token))
                return false;

            //si potrebbe gestire come scritto sopra nel secondo metodo
            if(comment==null)
                return false;

            DBManager.getInstance().getCommentDao().delete(comment);
            // TODO: 06/01/2023 valutare eliminazione solo con chiave e non passando tutto il commento
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    public Boolean deleteReview(Long person,Long event,String token){
        try {
            if(!authorizationControll.checkOwnerOrAdminAuthorization(person, token))
                return false;

            //si potrebbe gestire come scritto sopra nel secondo metodo addReview
            Review review=DBManager.getInstance().getReviewDao().findByPrimaryKey(person, event);

            if(review==null)
                return false;
            DBManager.getInstance().getReviewDao().delete(review);
            // TODO: 06/01/2023 valutare eliminazione solo con chiavi e non passando tutta la review
            return true;

        }catch (Exception e){
            //e.printStackTrace();
            return  false;
        }
    }





    //extra functions
    public LocalDateTime date(){
        LocalDateTime date = LocalDateTime.now();
        return date;
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
