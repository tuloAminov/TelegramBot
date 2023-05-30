package com.example.telegrambot.services;

import com.example.telegrambot.entities.Film;
import com.example.telegrambot.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    private final FilmRepository filmRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> getFilms() {
        return filmRepository.findAllByOrderByRankDesc();
    }

    public List<Film> getFilmsByName(String name) {
        return filmRepository.findAllByName(name);
    }

    public Film getFilmsById(Long id) {
        return filmRepository.getFilmById(id).get(0);
    }

    public List<Film> getFilmsByGenre(String genre) {
        return filmRepository.findAllByGenre(genre);
    }

    public List<Film> getFilmsByFilmDirector(String FilmDirector) {
        return filmRepository.findAllByFilmDirector(FilmDirector);
    }

    public List<Film> getFilmsByYear(int year) {
        return filmRepository.findAllByRank(year);
    }

    public List<Film> getFilmsByCountry(String country) {
        return filmRepository.findAllByCountry(country);
    }

    public List<Film> getFilmsByActor(Long actorId) {
        List<Film> films = new ArrayList<>();
        for (Long id : filmRepository.findFilmsByActor(actorId)) {
            films.add(filmRepository.getFilmById(id).get(0));
        }
        return films;
    }

    public void add(Long actor_id, Long film_id) {
        filmRepository.addActorFilm(actor_id, film_id);
    }

    public String getGenres() {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> genres = new ArrayList<>();
        int counter = 1;
        for (Film film : filmRepository.findAllByOrderByRankDesc()) {
            if (!genres.contains(film.getGenre())) {
                genres.add(film.getGenre());
                stringBuilder.append(counter).append(". ").append(film.getGenre()).append("\n");
                counter++;
            }
        }

        return stringBuilder.toString();
    }
}
