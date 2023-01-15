package it.sad.students.eventboard.service.httpbody;

import it.sad.students.eventboard.persistenza.model.EventType;
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
public class EditRequest {
    private Long id;
    private String name;
    private String lastName;
    private String password;
    private String email;
    private Long position;
    private List<Preference> preferences;
}
