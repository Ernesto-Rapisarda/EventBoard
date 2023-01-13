package it.sad.students.eventboard.persistenza.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event {
    private Long id = null;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private String title;
    private Double price;
    private Boolean soldOut;
    private String urlPoster;
    private String urlTicket;
    private String description;

    //chiavi esterne
    private EventType eventType;
    private Long position;
    private Long organizer;

    public Event() {
    }

    public Event(Long id, LocalDateTime date,String title, Double price, Boolean available, String urlPoster,String urlTicket, EventType eventType, Long luogo, Long person, String description) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.price = price;
        this.soldOut = available;
        this.urlPoster = urlPoster;
        this.urlTicket = urlTicket;
        this.eventType = eventType;
        this.position = luogo;
        this.organizer = person;
        this.description = description;
    }


    public String getUrlTicket() {
        return urlTicket;
    }

    public void setUrlTicket(String urlTicket) {
        this.urlTicket = urlTicket;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Boolean getSoldOut() {
        return soldOut;
    }
    public void setSoldOut(Boolean soldOut) {
        this.soldOut = soldOut;
    }
    public String getUrlPoster() {
        return urlPoster;
    }
    public void setUrlPoster(String urlPoster) {
        this.urlPoster = urlPoster;
    }
    public EventType getEventType() {
        return eventType;
    }
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
    public Long getPosition() {
        return position;
    }
    public void setPosition(Long position) {
        this.position = position;
    }
    public Long getOrganizer() {
        return organizer;
    }
    public void setOrganizer(Long organizer) {
        this.organizer = organizer;
    }
}
