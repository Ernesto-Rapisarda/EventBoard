package it.sad.students.eventboard.service.httpbody;

import it.sad.students.eventboard.persistenza.model.Preference;
import it.sad.students.eventboard.persistenza.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResponsePerson {
    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private Long position;
    private Role role;
    private Boolean locked;
    private List<Preference> preferences;
}
