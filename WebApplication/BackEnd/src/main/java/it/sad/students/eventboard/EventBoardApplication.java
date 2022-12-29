package it.sad.students.eventboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventBoardApplication.class, args);
        System.out.println("CI SONO RAGAZZI");
    }

}
