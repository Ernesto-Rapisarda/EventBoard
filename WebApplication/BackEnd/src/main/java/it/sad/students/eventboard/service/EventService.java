package it.sad.students.eventboard.service;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.Event;
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

    public Iterable<Event> getPreferredEvents(String username) {
        Long id = DBManager.getInstance().getPersonDao().findByUsername(username).getId();
        List<Preference> preferences= DBManager.getInstance().getPreferenceDao().findPreferences(id);
        List<Event> events = new ArrayList<>();
        for (Preference preference: preferences){
            events.addAll(DBManager.getInstance().getEventDao().findByType(preference.getEvent_type()));
        }

        return  events;
    }
}
