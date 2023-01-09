package it.sad.students.eventboard;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Event;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class EventBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventBoardApplication.class, args);
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");
		//LocalDateTime now = LocalDateTime.now();

		//System.out.println(dtf.format(now));


		//DemoData demoData=new DemoData();

	}

}
