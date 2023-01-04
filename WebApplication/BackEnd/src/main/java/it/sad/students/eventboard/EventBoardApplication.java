package it.sad.students.eventboard;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Person;
import it.sad.students.eventboard.persistenza.model.Position;
import it.sad.students.eventboard.persistenza.model.Role;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class EventBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventBoardApplication.class, args);
        /*Position position = new Position();
        DBManager.getInstance().getPositionDao().saveOrUpdate(position);

        Role role = new Role();
        role.setName("USER");
        DBManager.getInstance().getRoleDao().saveOrUpdate(role);

        Person person = new Person();
        person.setName("UserTest");
        person.setLastName("UserLastNameTest");
        person.setUsername("user");
        person.setPassword("passuser");
        person.setEmail("user@admin.it");
        person.setActiveStatus(true);
        person.setPosition(position.getId());
        person.setRole(role.getId());


        DBManager.getInstance().getPersonDao().saveOrUpdate(person);*/

    }

}
