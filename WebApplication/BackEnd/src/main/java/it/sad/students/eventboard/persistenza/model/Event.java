package it.sad.students.eventboard.persistenza.model;

import java.util.Date;

public class Event {
    private Long id;
    private Date date;
    private Double price;
    private Boolean available;
    private String urlPoster;

    //chiavi esterne
    private Long eventType;
    private Long luogo;
    private Long person;

    public Event() {
    }

    public Event(Long id, Date date, Double price, Boolean available, String urlPoster, Long eventType, Long luogo, Long person) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.available = available;
        this.urlPoster = urlPoster;
        this.eventType = eventType;
        this.luogo = luogo;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getUrlPoster() {
        return urlPoster;
    }

    public void setUrlPoster(String urlPoster) {
        this.urlPoster = urlPoster;
    }

    public Long getEventType() {
        return eventType;
    }

    public void setEventType(Long eventType) {
        this.eventType = eventType;
    }

    public Long getLuogo() {
        return luogo;
    }

    public void setLuogo(Long luogo) {
        this.luogo = luogo;
    }

    public Long getPerson() {
        return person;
    }

    public void setPerson(Long person) {
        this.person = person;
    }
}
