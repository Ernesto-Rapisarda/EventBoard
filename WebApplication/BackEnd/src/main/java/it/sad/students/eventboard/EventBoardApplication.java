package it.sad.students.eventboard;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class EventBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventBoardApplication.class, args);
        Position position = new Position();
        DBManager.getInstance().getPositionDao().saveOrUpdate(position);

        Role role = new Role();
        role.setName("ADMIN");
        DBManager.getInstance().getRoleDao().saveOrUpdate(role);

        Person person = new Person();
        person.setName("AdminTest");
        person.setLastName("AdminLastNameTest");
        person.setUsername("admin");
        person.setPassword("admin");
        person.setEmail("admin@admin.it");
        person.setActiveStatus(true);
        person.setPosition(position.getId());
        person.setRole(role.getId());


        DBManager.getInstance().getPersonDao().saveOrUpdate(person);

    }

}
