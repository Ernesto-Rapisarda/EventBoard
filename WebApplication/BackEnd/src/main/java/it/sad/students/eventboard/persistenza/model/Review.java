package it.sad.students.eventboard.persistenza.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Review {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private String message;
    private Integer rating;

    //chiavi esterne
    private Long person;
    private Long event;

    public Review(Long person, Long event, LocalDateTime date, String message, Integer rating) {
        this.person = person;
        this.event = event;
        this.date = date;
        this.message = message;
        this.rating = rating;
    }

    public Review(){}

    public Long getPerson() {
        return person;
    }

    public Long getEvent() {
        return event;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public Integer getRating() {
        return rating;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setPerson(Long person) {
        this.person = person;
    }

    public void setEvent(Long event) {
        this.event = event;
    }
}
