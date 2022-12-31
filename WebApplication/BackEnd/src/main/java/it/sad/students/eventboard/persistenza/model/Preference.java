package it.sad.students.eventboard.persistenza.model;

public class Preference {
    private Long person;
    private Long event_type;

    public Preference(Long person, Long event_type) {
        this.person = person;
        this.event_type = event_type;
    }

    public Preference() {}

    public Long getPerson() {
        return person;
    }

    public void setPerson(Long person) {
        this.person = person;
    }

    public Long getEvent_type() {
        return event_type;
    }

    public void setEvent_type(Long event_type) {
        this.event_type = event_type;
    }
}
