package it.sad.students.eventboard;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class EventBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventBoardApplication.class, args);

	}

}
