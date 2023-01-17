package it.sad.students.eventboard.service.httpbody;


import it.sad.students.eventboard.persistenza.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Role role;
}
