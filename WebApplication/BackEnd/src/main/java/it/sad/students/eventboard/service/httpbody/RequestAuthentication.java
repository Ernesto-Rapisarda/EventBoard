package it.sad.students.eventboard.service.httpbody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestAuthentication {

    private String username;// TODO: 04/01/2023 email???
    String password;

}
