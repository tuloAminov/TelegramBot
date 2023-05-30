package com.example.telegrambot.repositories;

import com.example.telegrambot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query(value = "insert into user_favorite_films (user_id, film_id) values (?, ?)", nativeQuery = true)
    @Transactional
    void addFavouriteFilm(@Param("user_id") Long user_id, @Param("film_id") Long film_id);

    @Modifying
    @Query(value = "insert into user_watch_list (user_id, film_id) values (?, ?)", nativeQuery = true)
    @Transactional
    void addWatchedFilm(@Param("user_id") Long user_id, @Param("film_id") Long film_id);

    @Modifying
    @Query(value = "insert into user_want_to_watch (user_id, film_id) values (?, ?)", nativeQuery = true)
    @Transactional
    void addFilmToWatch(@Param("user_id") Long user_id, @Param("film_id") Long film_id);

    @Modifying
    @Query(value = "insert into user_favorite_actors (user_id, actor_id) values (?, ?)", nativeQuery = true)
    @Transactional
    void addFavouriteActor(@Param("user_id") Long user_id, @Param("actor_id") Long actor_id);

    @Query(value = "select film_id from user_favorite_films where user_id = :user_id", nativeQuery = true)
    List<Long> getFavouriteFilms(@Param("user_id") Long user_id);

    @Query(value = "select film_id from user_watch_list where user_id = :user_id", nativeQuery = true)
    List<Long> getWatchedFilms(@Param("user_id") Long user_id);

    @Query(value = "select film_id from user_want_to_watch where user_id = :user_id", nativeQuery = true)
    List<Long> getFilmsToWatch(@Param("user_id") Long user_id);

    @Query(value = "select actor_id from user_favorite_actors where user_id = :user_id", nativeQuery = true)
    List<Long> getFavouriteActors(@Param("user_id") Long user_id);
}
