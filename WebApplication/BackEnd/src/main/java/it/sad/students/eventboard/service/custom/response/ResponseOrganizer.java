package it.sad.students.eventboard.service.custom.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResponseOrganizer {
    private String organizer;
    private String email;
    private List<ResponseEvent> events;
}
