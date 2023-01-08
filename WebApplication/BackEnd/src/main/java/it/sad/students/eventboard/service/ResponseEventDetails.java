package it.sad.students.eventboard.service;

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
    List<Comment> commentList;
    List<Like> likeList;
    List<Partecipation> partecipationList;
    List<Review> reviewList;
}

/*card eventi:
immagine,
id, titolo
data, ora, luogo
Nome organizzatore
 */

/*full details dell'evento:
    Event event;
    List<Comment> commentList;
    List<Like> likeList;
    List<Partecipation> partecipationList;
    List<Review> reviewList;

 */