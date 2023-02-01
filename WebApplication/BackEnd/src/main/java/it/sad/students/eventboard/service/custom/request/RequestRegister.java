package it.sad.students.eventboard.service.custom.request;


import it.sad.students.eventboard.persistenza.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestRegister {
    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Role role;
}
