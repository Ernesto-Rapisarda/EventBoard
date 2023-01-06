package it.sad.students.eventboard.service;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.EventType;
import it.sad.students.eventboard.persistenza.model.Preference;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    public Iterable<Event> getAllEvents(){
        return DBManager.getInstance().getEventDao().findAll();
    }

    public Iterable<Event> getPreferredEvents(List<EventType> eventTypes) {

        List<Event> events = new ArrayList<>();
        for (EventType eventType: eventTypes){
            events.addAll(DBManager.getInstance().getEventDao().findByType(eventType.getId()));
        }

        return  events;
    }

    public Boolean createEvent(Event event) {


        return DBManager.getInstance().getEventDao().saveOrUpdate(event);
    }
}
