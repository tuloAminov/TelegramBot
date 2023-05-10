package com.example.telegrambot.repositories;

import com.example.telegrambot.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    List<Film> findAllByOrderByRankDesc();

    List<Film> findAllByName(String name);

    List<Film> findAllByGenre(String genre);

    List<Film> findAllByRank(double rank);

    List<Film> findAllByFilmDirector(String filmDirector);

    List<Film> findAllByYear(Integer year);

    List<Film> findAllByCountry(String country);
}
