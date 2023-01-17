package it.sad.students.eventboard.service.httpbody;

import it.sad.students.eventboard.persistenza.model.EventType;
import it.sad.students.eventboard.persistenza.model.Position;
import it.sad.students.eventboard.persistenza.model.Preference;
import it.sad.students.eventboard.persistenza.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePerson {
    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private Role role;
    private Boolean is_not_locked;
    private List<Preference> preferences;
    private Position position;
}
