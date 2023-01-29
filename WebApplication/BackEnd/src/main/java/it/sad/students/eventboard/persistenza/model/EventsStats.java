package it.sad.students.eventboard.persistenza.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventsStats {
    private Long id;
    private String title;
    private String urlImage;
    private Double value;
}
