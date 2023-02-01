package it.sad.students.eventboard.persistenza.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class Person implements UserDetails {

    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String email;

    private Boolean enabled; //ex activeStatus
    private Boolean is_not_locked; //ban

    //chiavi esterne
    private Long position=null;
    private Role role;

    public Person(){}

    public Person(Long id, String name, String lastName, String username, String password, String email, Long position, Role role) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return is_not_locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setIs_not_locked(Boolean is_not_locked) {
        this.is_not_locked = is_not_locked;
    }


    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Long getPosition() {
        return position;
    }

    public Role getRole() {
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

    public void setRole(Role role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setPosition(Long position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", is_not_locked=" + is_not_locked +
                ", position=" + position +
                ", role=" + role +
                '}';
    }
}
