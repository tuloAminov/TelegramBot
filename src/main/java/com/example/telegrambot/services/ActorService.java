package com.example.telegrambot.services;

import com.example.telegrambot.entities.Actor;
import com.example.telegrambot.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {
    private final ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<Actor> findAll() {
        return actorRepository.findAllByOrderByName();
    }

    public void save(Actor actor) {
        actorRepository.save(actor);
    }

}
