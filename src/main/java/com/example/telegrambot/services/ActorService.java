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

    public String getActors() {
        List<Actor> actors = actorRepository.findAllByOrderByName();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < actors.size() + 1; i++)
            stringBuilder.append(i).append(". ").append(actors.get(i-1).toString()).append("\n");

        return stringBuilder.toString();
    }

    public List<Actor> findAllByNameAndSurname(String name, String surname) {
        return actorRepository.findAllByNameAndSurname(name, surname);
    }

    public List<Actor> findAllByAge(Integer age) {
        return actorRepository.findAllByAge(age);
    }

    public List<Actor> findAllByCountry(String country) {
        return actorRepository.findAllByCountry(country);
    }
}
