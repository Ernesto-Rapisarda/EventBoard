package it.sad.students.eventboard.persistenza.model;

public class Position {
    private Long id;
    private String region;
    private String city;
    private String address;
    private Double longitude;
    private Double latitude;

    public Position(){}
    public Position(Long id, String region, String city, String address, Double longitude, Double latitude) {
        this.id = id;
        this.region = region;
        this.city = city;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
