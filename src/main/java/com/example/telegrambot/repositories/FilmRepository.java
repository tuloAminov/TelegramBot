package com.example.telegrambot.repositories;

import com.example.telegrambot.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    List<Film> findAllByOrderByRankDesc();

    List<Film> findAllByName(String name);

    List<Film> getFilmById(Long id);

    List<Film> findAllByGenre(String genre);

    List<Film> findAllByRank(double rank);

    List<Film> findAllByFilmDirector(String filmDirector);

    List<Film> findAllByYear(Integer year);

    List<Film> findAllByCountry(String country);

    @Query(value = "select film_id from actors_films where actor_id = :actor_id", nativeQuery = true)
    List<Long> findFilmsByActor(@Param("actor_id") Long actor_id);

    @Modifying
    @Query(value = "insert into actors_films (actor_id, film_id) values (?, ?)", nativeQuery = true)
    @Transactional
    void addActorFilm(@Param("actor_id") Long actor_id, @Param("film_id") Long film_id);
}
