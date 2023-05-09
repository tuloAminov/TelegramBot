package com.example.telegrambot.services;

import com.example.telegrambot.entities.Actor;
import com.example.telegrambot.repositories.ActorRepository;
import com.example.telegrambot.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actor/film")
public class Controller {

    private final ActorRepository actorRepository;
    private final FilmRepository filmRepository;

    @Autowired
    public Controller(ActorRepository actorRepository, FilmRepository filmRepository) {
        this.actorRepository = actorRepository;
        this.filmRepository = filmRepository;
    }

    @PostMapping
    public Actor saveActorWithFilm(@RequestBody Actor actor) {
        return actorRepository.save(actor);
    }

    @GetMapping
    public List<Actor> findAllActors() {
        return  actorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Actor findActor(@PathVariable Long id) {
        return actorRepository.findById(id).orElse(null);
    }
}
