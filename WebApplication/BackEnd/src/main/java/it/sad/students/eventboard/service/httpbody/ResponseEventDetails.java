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
    // TODO: 10/01/2023 nome + cognome 
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