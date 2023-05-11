package com.example.telegrambot.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "film")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String genre;
    private double rank;
    private String filmDirector;
    @ManyToMany(mappedBy = "films", fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Actor> actors = new ArrayList<>();
    private Integer year;
    private String country;

    @ManyToMany(mappedBy = "watchList", fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    private List<User> userWatchList = new ArrayList<>();

    @ManyToMany(mappedBy = "favoriteFilms", fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    private List<User> userFavoriteFilms = new ArrayList<>();

    @ManyToMany(mappedBy = "wantToWatch", fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    private List<User> userWantToWatch = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public String getFilmDirector() {
        return filmDirector;
    }

    public void setFilmDirector(String filmDirector) {
        this.filmDirector = filmDirector;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<User> getUserWatchList() {
        return userWatchList;
    }

    public void setUserWatchList(List<User> userWatchList) {
        this.userWatchList = userWatchList;
    }

    public List<User> getUserFavoriteFilms() {
        return userFavoriteFilms;
    }

    public void setUserFavoriteFilms(List<User> userFavoriteFilms) {
        this.userFavoriteFilms = userFavoriteFilms;
    }

    public List<User> getUserWantToWatch() {
        return userWantToWatch;
    }

    public void setUserWantToWatch(List<User> userWantToWatch) {
        this.userWantToWatch = userWantToWatch;
    }

    @Override
    public String toString() {
        return name + ", " + genre + ", " + rank;
    }
}
