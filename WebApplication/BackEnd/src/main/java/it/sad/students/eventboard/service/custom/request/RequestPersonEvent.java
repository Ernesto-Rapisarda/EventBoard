package it.sad.students.eventboard.service.custom.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestPersonEvent {
    private Long person;
    private Long event;
}
