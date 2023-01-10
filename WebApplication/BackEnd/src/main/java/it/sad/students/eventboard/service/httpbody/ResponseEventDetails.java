package it.sad.students.eventboard.service.httpbody;

import it.sad.students.eventboard.persistenza.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResponseEventDetails {
    Event event;
    String organizerFullName;
    List<ResponseComment> commentList;
    List<Like> likeList;
    List<Partecipation> partecipationList;
    List<ResponseReview> reviewList;
}

