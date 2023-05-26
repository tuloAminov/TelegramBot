package com.example.telegrambot.controllers;

import com.example.telegrambot.entities.Actor;
import com.example.telegrambot.entities.Film;
import com.example.telegrambot.entities.User;
import com.example.telegrambot.repositories.ActorRepository;
import com.example.telegrambot.repositories.FilmRepository;
import com.example.telegrambot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    private final ActorRepository actorRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    @Autowired
    public Controller(ActorRepository actorRepository, FilmRepository filmRepository, UserRepository userRepository) {
        this.actorRepository = actorRepository;
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/addActor")
    public Actor saveActor(@RequestBody Actor actor) {
        return actorRepository.save(actor);
    }

    @PostMapping("/addFilm")
    public Film saveFilm(@RequestBody Film film) {
        return filmRepository.save(film);
    }

    @PostMapping("/addUser")
    public User saveUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/actors")
    public List<Actor> findAllActors() {
        return  actorRepository.findAll();
    }

    @GetMapping("/films")
    public List<Film> findAllFilms() {
        return  filmRepository.findAll();
    }

    @GetMapping("/actor/{id}")
    public Actor findActor(@PathVariable Long id) {
        return actorRepository.findById(id).orElse(null);
    }

    @GetMapping("/film/{id}")
    public Film findFilm(@PathVariable Long id) {
        return filmRepository.findById(id).orElse(null);
    }

    @GetMapping("/filmByName/{name}")
    public Film findFilmByName(@PathVariable String name) {
        return filmRepository.findAllByName(name).get(0);
    }

    @GetMapping("/actorFilms/{id}")
    public List<Film> actorFilms(@PathVariable Long id) {
        return actorRepository.getReferenceById(id).getFilms();
    }

    @GetMapping("/filmActors/{id}")
    public List<Actor> filmActors(@PathVariable Long id) {
        return filmRepository.getReferenceById(id).getActors();
    }

}