package com.dominikazb.beans;


import com.dominikazb.validators.UniqueUsername;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @UniqueUsername(message = "Such username already exists!")
    @Column(name = "username", unique = true)
    private String username;

    @Size(min = 4, message = "Password should have at least 4 characters.")
    @Column(name = "password")
    private String password;

    @Size(min = 2, message = "First name should have at least 2 characters.")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "user_active")
    private boolean active;

    @Column(name = "user_roles")
    private String roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Note> notes;

    public User() {
    }

    public User(String username, String password, String firstName, boolean active, String roles) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.active = active;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
