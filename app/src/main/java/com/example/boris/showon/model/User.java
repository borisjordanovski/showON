package com.example.boris.showon.model;

import java.util.List;

/**
 * Created by Boris on 19-Jul-17.
 */

public class User {
    private String name;
    private String lastName;
    private String country;
    private String email;
    private List<String> genres;

    public User(){}

    public User(String name, String lastName, String country, String email, List<String> genres) {
        this.name = name;
        this.lastName = lastName;
        this.country = country;
        this.email = email;
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
