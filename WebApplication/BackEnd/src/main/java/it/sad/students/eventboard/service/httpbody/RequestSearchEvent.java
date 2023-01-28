package it.sad.students.eventboard.service.httpbody;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class RequestSearchEvent {
    String searchType; //può essere dateAndOrLocation or title,

    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate initialRangeDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate finalRangeDate;

    String city;
    String region;
    String title;

}
