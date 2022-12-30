package it.sad.students.eventboard.persistenza.model;

import java.util.Date;

public class Comment {
    private Long id;
    private Date date;
    private String message;

    //chiavi esterne
    private Long person;

    public Comment() {
    }

    public Comment(Long id, Date date, String message, Long person) {
        this.id = id;
        this.date = date;
        this.message = message;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getPerson() {
        return person;
    }

    public void setPerson(Long person) {
        this.person = person;
    }
}
