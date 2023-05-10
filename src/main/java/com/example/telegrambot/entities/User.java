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
/*    private List<Long> watchList;
    private List<Long> favoriteFilms;
    private List<Long> wantToWatch;
    private List<Long> favoriteActors;*/
}