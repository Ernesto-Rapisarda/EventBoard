package it.sad.students.eventboard;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.EventType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class EventBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventBoardApplication.class, args);
        /*EventType eventType = new EventType();
        eventType.setDescription("fdsfds");
        eventType.setName("amministratori");
        DBManager.getInstance().getEventTypeDao().saveOrUpdate(eventType);
*/
    }

}
