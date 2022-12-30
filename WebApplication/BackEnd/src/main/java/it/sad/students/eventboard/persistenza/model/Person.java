package it.sad.students.eventboard.persistenza.model;

public class Person {
    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Boolean activeStatus;

    //chiavi esterne
    private Long position;
    private Long role;

    public Person(){}

    public Person(Long id, String name, String lastName, String username, String password, String email, Boolean activeStatus, Long position, Long role) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.activeStatus = activeStatus;
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

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public Long getPosition() {
        return position;
    }

    public Long getRole() {
        return role;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActiveStatus(Boolean active) {
        this.activeStatus = active;
    }

    public void setPosition(Long position) {
        this.position = position;
    }
}
