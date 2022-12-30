package it.sad.students.eventboard.persistenza.model;

import java.util.Date;

public class Report {
    private Long id;
    private Boolean status;
    private String message;
    private Date date;

    //chiavi esterne
    private Long type;
    private Long person;

    public Report(){}

    public Report(Long id, Long type, Boolean status, String message, Date date, Long person) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.message = message;
        this.date = date;
        this.person = person;
    }

    public Boolean getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getPerson() {
        return person;
    }

    public void setPerson(Long person) {
        this.person = person;
    }
}
