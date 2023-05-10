package com.example.telegrambot.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private List<Long> watchList;
    private Long favoriteFilms;
    private ArrayList<Film> wantToWatch;
    private ArrayList<Actor> favoriteActors;
}