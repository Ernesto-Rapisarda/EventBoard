package it.sad.students.eventboard.persistenza.model;

import java.util.Date;

public class Event {
    private Long id = null;
    private Date date;
    private Double price;
    private Boolean soldOut;
    private String urlPoster;
    private String description;

    //chiavi esterne
    private Long eventType;
    private Long position;
    private Long publisher;

    public Event() {
    }

    public Event(Long id, Date date, Double price, Boolean available, String urlPoster, Long eventType, Long luogo, Long person, String description) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.soldOut = available;
        this.urlPoster = urlPoster;
        this.eventType = eventType;
        this.position = luogo;
        this.publisher = person;
        this.description = description;
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

    public Long getEventType() {
        return eventType;
    }

    public void setEventType(Long eventType) {
        this.eventType = eventType;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public Long getPublisher() {
        return publisher;
    }

    public void setPublisher(Long publisher) {
        this.publisher = publisher;
    }
}
