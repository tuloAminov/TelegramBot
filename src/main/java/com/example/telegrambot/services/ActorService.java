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

    public List<Actor> getActors() {
        return actorRepository.findAllByOrderByName();
    }

    public List<Actor> getActorsByNameOrSurname(String name) {
        return actorRepository.findAllByNameOrSurname(name, name);
    }

    public List<Actor> getActorsByNameAndSurname(String name, String surname) {
        return actorRepository.findAllByNameAndSurname(name, surname);
    }

    public List<Actor> getActorsByAge(int age) {
        return actorRepository.findAllByAge(age);
    }

    public List<Actor> getActorsByCountry(String country) {
        return actorRepository.findAllByCountry(country);
    }
}
