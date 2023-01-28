package it.sad.students.eventboard.persistenza.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GlobalStats {
    private Integer nEvents;
    private Integer nComments;
    private Integer nReviews;
    private Integer nLike;
    private Integer nPartecipation;
}
