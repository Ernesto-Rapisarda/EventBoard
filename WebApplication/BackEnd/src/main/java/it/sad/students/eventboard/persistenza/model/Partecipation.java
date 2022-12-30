package it.sad.students.eventboard.persistenza.model;

import java.util.Date;

public class Partecipation {

    private Date date;

    //chiavi esterne
    private Long person;
    private Long event;

    public Partecipation(Date date, Long person, Long event) {
        this.date = date;
        this.person = person;
        this.event = event;
    }

    public Date getDate() {
        return date;
    }

    public Long getPerson() {
        return person;
    }

    public Long getEvent() {
        return event;
    }
}
