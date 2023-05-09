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

    public List<Film> findAllByRating() {
        return filmRepository.findAllByOrderByRank();
    }
}
