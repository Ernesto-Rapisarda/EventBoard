package it.sad.students.eventboard.service;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Like;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class InteractionService {
    public boolean setLike(Long person,Long event,Boolean status){//true=Mi piace
        if(status){
            Like like=DBManager.getInstance().getLikeDao().findByPrimaryKey(person,event);
            DBManager.getInstance().getLikeDao().delete(like);
            return false;
        }else {
            DBManager.getInstance().getLikeDao().saveOrUpdate(new Like(person, event, LocalDate.from(date())));
            return true;
        }
    }

    public boolean setPartecipation(Long person,Long event,Boolean status){//true=Mi piace
        if(status){
            Like like=DBManager.getInstance().getLikeDao().findByPrimaryKey(person,event);
            DBManager.getInstance().getLikeDao().delete(like);
            return false;
        }else {
            DBManager.getInstance().getLikeDao().saveOrUpdate(new Like(person, event, LocalDate.from(date())));
            return true;
        }
    }


    public LocalDateTime date(){
        LocalDateTime date = LocalDateTime.now();
        return date;
    }

}
