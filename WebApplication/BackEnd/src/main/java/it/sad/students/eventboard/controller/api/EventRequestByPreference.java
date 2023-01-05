package it.sad.students.eventboard.controller.api;

import it.sad.students.eventboard.persistenza.model.Preference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestByPreference {


    private List<Preference> preferenceList;


}
