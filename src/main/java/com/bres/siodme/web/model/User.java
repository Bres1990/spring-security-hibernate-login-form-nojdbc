package com.bres.siodme.web.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by Adam on 2016-07-29.
 */

@Entity
@Table(name = "Users")
public class User {
    @Column(name = "id")
    private Long id;

    @Size(min=6, max=32, message="Username must fit between 6 and 32 letters") @NotEmpty
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @Size(min=8, max=60, message="Password must fit between 8 and 60 letters") @NotEmpty
    private String password;

    @Column(name = "role")
    private String role;

    protected User() {};

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }


    @Override
    public String toString() {
        return String.format(
                "User [ id = '%d', username ='%s', password ='%s' ]",
                id, username, password);
    }
}

