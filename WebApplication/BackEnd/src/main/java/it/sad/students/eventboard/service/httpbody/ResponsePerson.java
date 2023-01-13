package it.sad.students.eventboard.service.httpbody;

import it.sad.students.eventboard.persistenza.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
}
