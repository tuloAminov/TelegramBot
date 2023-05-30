package com.example.telegrambot.repositories;

import com.example.telegrambot.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    List<Actor> findAllByOrderByName();

    List<Actor> getActorById(Long id);

    List<Actor> findAllByNameOrSurname(String name, String surname);

    List<Actor> findAllByAge(Integer age);

    List<Actor> findAllByCountry(String country);

    List<Actor> findAllByNameAndSurname(String name, String surname);

    @Query(value = "select actor_id from actors_films where film_id = :film_id", nativeQuery = true)
    List<Long> findActorsByFilm(@Param("film_id") Long film_id);
}