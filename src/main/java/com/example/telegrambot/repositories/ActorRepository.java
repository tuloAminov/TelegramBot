package com.example.telegrambot.repositories;

import com.example.telegrambot.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    List<Actor> findAllByOrderByName();

    List<Actor> findAllByNameOrSurname(String name, String surname);

    List<Actor> findAllByAge(Integer age);

    List<Actor> findAllByCountry(String country);

}