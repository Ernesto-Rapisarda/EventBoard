package it.sad.students.eventboard.security.auth;


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
    // TODO: 04/01/2023 settare i nostri parametri 

    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Boolean activeStatus;
    private Long position;
    private Role role;
}
