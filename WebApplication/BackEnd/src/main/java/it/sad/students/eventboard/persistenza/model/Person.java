package it.sad.students.eventboard.persistenza.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class Person implements UserDetails {

    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Boolean activeStatus;

    private Boolean expired;
    private Boolean locked;
    private Boolean credExpired;
    public Boolean isEnabled;

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

    @Override
    public boolean isAccountNonExpired() {
        return expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public void setCredExpired(Boolean credExpired) {
        this.credExpired = credExpired;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
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
