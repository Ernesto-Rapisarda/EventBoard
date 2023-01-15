package it.sad.students.eventboard.persistenza.model;

public class Preference {
    private Long person;
    private EventType event_type;



    public Preference(Long person, EventType event_type) {
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

    public EventType getEvent_type() {
        return event_type;
    }

    public void setEvent_type(EventType event_type) {
        this.event_type = event_type;
    }
}
