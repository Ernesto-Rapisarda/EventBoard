package it.sad.students.eventboard.service.httpbody;

import it.sad.students.eventboard.persistenza.model.Position;
import it.sad.students.eventboard.persistenza.model.Preference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserEdit {
    private Long id;
    private String name;
    private String lastName;
    private String password;
    private String email;
    private Position position;
    private List<Preference> preferences;
}
