package it.sad.students.eventboard.persistenza.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Preference that)) return false;
        return person.equals(that.person) && event_type == that.event_type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, event_type);
    }
}
