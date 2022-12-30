package it.sad.students.eventboard.persistenza.model;

import java.util.Date;

public class Like {
    //chiavi esterne
    private Long person;
    private Long idEvent;

    private Date date;

    public Like (){}

    public Like(Long person, Long idEvent, Date date) {
        this.person = person;
        this.idEvent = idEvent;
        this.date = date;
    }

    public Long getPerson() {
        return person;
    }

    public void setPerson(Long person) {
        this.person = person;
    }

    public Long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
