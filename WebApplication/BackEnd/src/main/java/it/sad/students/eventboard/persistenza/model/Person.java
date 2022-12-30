package it.sad.students.eventboard.persistenza.model;

public class Person {
    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Boolean active;

    //chiavi esterne
    private Long position;
    private Long role;

    public Person(Long id, String name, String lastName, String username, String password, String email, Boolean active, Long position, Long role) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.active = active;
        this.position = position;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getActive() {
        return active;
    }

    public Long getPosition() {
        return position;
    }

    public Long getRole() {
        return role;
    }





    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setPosition(Long position) {
        this.position = position;
    }
}
