package com.example.telegrambot.services;

import com.example.telegrambot.entities.Film;
import com.example.telegrambot.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {
    private final FilmRepository filmRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> findAllOrderByRating() {
        return filmRepository.findAllByOrderByRankDesc();
    }

    public List<Film> findAllByName(String name) {
        return filmRepository.findAllByName(name);
    }

    public List<Film> findAllByGenre(String genre) {
        return filmRepository.findAllByGenre(genre);
    }

    public List<Film> findAllByRank(double rank) {
        return filmRepository.findAllByRank(rank);
    }

    public List<Film> findAllByDirector(String director) {
        return filmRepository.findAllByFilmDirector(director);
    }

    public List<Film> findAllByYear(Integer year) {
        return filmRepository.findAllByYear(year);
    }

    public List<Film> findAllByCountry(String country) {
        return filmRepository.findAllByCountry(country);
    }
}
