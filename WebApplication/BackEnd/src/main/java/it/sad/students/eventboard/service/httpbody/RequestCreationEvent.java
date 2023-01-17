package it.sad.students.eventboard.service.httpbody;

import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestCreationEvent {
    private Event event;
    private Position position;
}
