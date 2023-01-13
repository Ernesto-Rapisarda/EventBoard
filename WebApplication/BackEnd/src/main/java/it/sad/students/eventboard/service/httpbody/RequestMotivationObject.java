package it.sad.students.eventboard.service.httpbody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestMotivationObject<T> {
    private T object;
    private String message; //(utile solo per admin)
}
