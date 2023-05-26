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

    public String getFilms() {
        List<Film> films = filmRepository.findAllByOrderByRankDesc();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < films.size() + 1; i++)
            stringBuilder.append(i).append(". ").append(films.get(i-1).toString()).append("\n");

        return stringBuilder.toString();
    }

    public String getFilmsByName(String name) {
        List<Film> films = filmRepository.findAllByName(name);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < films.size() + 1; i++)
            stringBuilder.append(i).append(". ").append(films.get(i-1).toString()).append("\n");

        return stringBuilder.toString();
    }

    public String getFilmsByGenre(String genre) {
        List<Film> films = filmRepository.findAllByGenre(genre);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < films.size() + 1; i++)
            stringBuilder.append(i).append(". ").append(films.get(i-1).toString()).append("\n");

        return stringBuilder.toString();
    }

    public String getFilmsByFilmDirector(String FilmDirector) {
        List<Film> films = filmRepository.findAllByFilmDirector(FilmDirector);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < films.size() + 1; i++)
            stringBuilder.append(i).append(". ").append(films.get(i-1).toString()).append("\n");

        return stringBuilder.toString();
    }

    public String getFilmsByYear(int year) {
        List<Film> films = filmRepository.findAllByRank(year);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < films.size() + 1; i++)
            stringBuilder.append(i).append(". ").append(films.get(i-1).toString()).append("\n");

        return stringBuilder.toString();
    }

    public String getFilmsByCountry(String country) {
        List<Film> films = filmRepository.findAllByCountry(country);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < films.size() + 1; i++)
            stringBuilder.append(i).append(". ").append(films.get(i-1).toString()).append("\n");

        return stringBuilder.toString();
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
