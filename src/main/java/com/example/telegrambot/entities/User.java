package com.example.telegrambot.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_watchList",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"))
    @JsonIdentityReference(alwaysAsId = true)
    private List<Film> watchList = new ArrayList<>();
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_favoriteFilms",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"))
    @JsonIdentityReference(alwaysAsId = true)
    private List<Film> favoriteFilms = new ArrayList<>();
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_wantToWatch",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"))
    @JsonIdentityReference(alwaysAsId = true)
    private List<Film> wantToWatch = new ArrayList<>();
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_favoriteActors",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id"))
    @JsonIdentityReference(alwaysAsId = true)
    private List<Actor> favoriteActors = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Film> getWatchList() {
        return watchList;
    }

    public void setWatchList(List<Film> watchList) {
        this.watchList = watchList;
    }

    public List<Film> getFavoriteFilms() {
        return favoriteFilms;
    }

    public void setFavoriteFilms(List<Film> favoriteFilms) {
        this.favoriteFilms = favoriteFilms;
    }

    public List<Film> getWantToWatch() {
        return wantToWatch;
    }

    public void setWantToWatch(List<Film> wantToWatch) {
        this.wantToWatch = wantToWatch;
    }

    public List<Actor> getFavoriteActors() {
        return favoriteActors;
    }

    public void setFavoriteActors(List<Actor> favoriteActors) {
        this.favoriteActors = favoriteActors;
    }
}