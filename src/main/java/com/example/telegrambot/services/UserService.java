package com.example.telegrambot.services;

import com.example.telegrambot.entities.Actor;
import com.example.telegrambot.entities.Film;
import com.example.telegrambot.entities.User;
import com.example.telegrambot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final FilmService filmService;
    private final ActorService actorService;

    @Autowired
    public UserService(UserRepository userRepository, FilmService filmService, ActorService actorService) {
        this.userRepository = userRepository;
        this.filmService = filmService;
        this.actorService = actorService;
    }

    public List<Film> getFavFilms(Long userId) {
        List<Film> films = new ArrayList<>();
        for (Long id : userRepository.getFavouriteFilms(userId)) {
            films.add(filmService.getFilmsById(id));
        }

        return films;
    }

    public List<Film> getWatchedFilm(Long userId) {
        List<Film> films = new ArrayList<>();
        for (Long id : userRepository.getWatchedFilms(userId)) {
            films.add(filmService.getFilmsById(id));
        }

        return films;
    }

    public List<Film> getFilmToWatch(Long userId) {
        List<Film> films = new ArrayList<>();
        for (Long id : userRepository.getFilmsToWatch(userId)) {
            films.add(filmService.getFilmsById(id));
        }

        return films;
    }

    public List<Actor> getFavouriteActor(Long userId) {
        List<Actor> actors = new ArrayList<>();
        for (Long id : userRepository.getFavouriteActors(userId)) {
            actors.add(actorService.getActorById(id));
        }

        return actors;
    }


    public void addFavouriteFilm(Long userId, Long filmId) {
        userRepository.addFavouriteFilm(userId, filmId);
    }

    public void addWatchedFilm(Long userId, Long filmId) {
        userRepository.addWatchedFilm(userId, filmId);
    }

    public void addFilmToWatch(Long userId, Long filmId) {
        userRepository.addFilmToWatch(userId, filmId);
    }

    public void addFavouriteActor(Long userId, Long actorId) {
        userRepository.addFavouriteActor(userId, actorId);
    }

    public void addUser(User user) {
        List<Long> ids = new ArrayList<>();
        for (User u: userRepository.findAll()) {
            ids.add(u.getId());
        }

        if (!ids.contains(user.getId()))
            userRepository.save(user);
    }
}
