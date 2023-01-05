package it.sad.students.eventboard.persistenza.model;

import java.time.LocalDate;
import java.util.Date;

public class Partecipation {

    private LocalDate date;

    //chiavi esterne
    private Long person;
    private Long event;

    public Partecipation(LocalDate date, Long person, Long event) {
        this.date = date;
        this.person = person;
        this.event = event;
    }
    public Partecipation(){}

    public LocalDate getDate() {
        return date;
    }

    public Long getPerson() {
        return person;
    }

    public Long getEvent() {
        return event;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPerson(Long person) {
        this.person = person;
    }

    public void setEvent(Long event) {
        this.event = event;
    }
}
