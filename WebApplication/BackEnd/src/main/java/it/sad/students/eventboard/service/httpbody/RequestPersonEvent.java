package it.sad.students.eventboard.service.httpbody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestPersonEvent {
    private Long person;
    private Long event;
}
