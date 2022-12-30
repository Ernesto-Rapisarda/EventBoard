package it.sad.students.eventboard.persistenza.model;

import java.util.Date;

public class Review {

    private Date date;
    private String text;
    private Integer rating;

    //chiavi esterne
    private Long person;
    private Long event;

    public Review(Long person, Long event, Date date, String text, Integer rating) {
        this.person = person;
        this.event = event;
        this.date = date;
        this.text = text;
        this.rating = rating;
    }

    public Long getPerson() {
        return person;
    }

    public Long getEvent() {
        return event;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public Integer getRating() {
        return rating;
    }
}
