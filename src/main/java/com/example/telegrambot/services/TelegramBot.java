package com.example.telegrambot.services;

import com.example.telegrambot.config.BotConfig;
import com.example.telegrambot.entities.User;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final ActorService actorService;
    private final FilmService filmService;
    private final UserService userService;

    @Autowired
    public TelegramBot(BotConfig config, ActorService actorService, FilmService filmService, UserService userService) {
        this.config = config;
        this.actorService = actorService;
        this.filmService = filmService;
        this.userService = userService;
        List<BotCommand> commandList = new ArrayList<>();
        commandList.add(new BotCommand("/films", "фильмы"));
        commandList.add(new BotCommand("/actors", "актеры"));
        commandList.add(new BotCommand("/genres", "жанры"));
        commandList.add(new BotCommand("/favfilms", "понравившиеся фильмы"));
        commandList.add(new BotCommand("/watchlist", "буду смотреть"));
        commandList.add(new BotCommand("/watched", "просмотренные фильмы"));
        commandList.add(new BotCommand("/myactors", "любимые актеры"));
        try {
            this.execute(new SetMyCommands(commandList, new BotCommandScopeDefault(), null));
        }
        catch (TelegramApiException e){
            log.info("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.contains("Film: ")) {
                String text = putInString(filmService.getFilmsByName(messageText.replace("Film: ", "")));
                if (text.isEmpty())
                    text = "there is no such movie";
                sendMessage(chatId, text);
            }

            else if (messageText.contains("Genre: ")) {
                String text = putInString(filmService.getFilmsByGenre(messageText.replace("Genre: ", "")));
                if (text.isEmpty())
                    text = "there is no such genre";
                sendMessage(chatId, text);
            }

            else if (messageText.contains("Film director: ")) {
                String text = putInString(filmService.getFilmsByFilmDirector(messageText.replace("Film director: ", "")));
                if (text.isEmpty())
                    text = "there is no such director";
                sendMessage(chatId, text);
            }

            else if (messageText.contains("Country: ")) {
                String text = putInString(filmService.getFilmsByCountry(messageText.replace("Country: ", "")));
                if (text.isEmpty())
                    text = "there is no such country";
                sendMessage(chatId, text);

                String text1 = putInString(actorService.getActorsByCountry(messageText.replace("Country: ", "")));
                if (text1.isEmpty())
                    text1 = "there is no such country";
                sendMessage(chatId, text1);
            }

            else if (messageText.contains("Actor: ")) {
                String[] str = messageText.replace("Actor's films: ", "").split(" ");
                String text = putInString(actorService.getActorsByNameAndSurname(str[0], str[1]));
                if (text.isEmpty())
                    text = "there is no such actor";
                sendMessage(chatId, text);
            }

            else if (messageText.contains("Actor's films: ")) {
                String[] str = messageText.replace("Actor's films: ", "").split(" ");
                String text = putInString(actorService.getActorsByNameAndSurname(str[0], str[1]));
                if (text.isEmpty())
                    text = "there is no such actor";
                sendMessage(chatId, text);
            }

            else if (messageText.contains("Fav film: ")) {
                long filmId = filmService.getFilmsByName(messageText.replace("Fav film: ", "")).get(0).getId();
                userService.addFavouriteFilm(chatId, filmId);
            }

            else if (messageText.contains("Watched: ")) {
                long filmId = filmService.getFilmsByName(messageText.replace("Watched: ", "")).get(0).getId();
                userService.addWatchedFilm(chatId, filmId);
            }

            else if (messageText.contains("To watch: ")) {
                long filmId = filmService.getFilmsByName(messageText.replace("To watch: ", "")).get(0).getId();
                userService.addFilmToWatch(chatId, filmId);
            }

            else if (messageText.contains("Fav actor: ")) {
                String[] fullName = messageText.replace("Fav actor: ", "").split(" ");
                long filmId = actorService.getActorsByNameAndSurname(fullName[0], fullName[1]).get(0).getId();
                userService.addFavouriteActor(chatId, filmId);
            }

            else {
                switch (messageText) {
                    case "/start" -> startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    case "/films" -> sendMessage(chatId, putInString(filmService.getFilms()));
                    case "/actors" -> sendMessage(chatId, putInString(actorService.getActors()));
                    case "/genres" -> sendMessage(chatId, filmService.getGenres());
                    case "/favfilms" -> sendMessage(chatId, putInString(userService.getFavFilms(chatId)));
                    case "/watchlist" -> sendMessage(chatId, putInString(userService.getFilmToWatch(chatId)));
                    case "/watched" -> sendMessage(chatId, putInString(userService.getWatchedFilm(chatId)));
                    case "/myactors" -> sendMessage(chatId, putInString(userService.getFavouriteActor(chatId)));
                    default -> sendMessage(chatId, "sorry, command was not recognized");
                }
            }
        }
    }

    private void startCommandReceived(long chatId, String firstName) {
        String answer = EmojiParser.parseToUnicode("Hi, " + firstName + ", nice to meet you!" + " :blush:");
        log.info("Replied to user " + firstName);
        User newUser = new User();
        newUser.setId(chatId);
        userService.addUser(newUser);
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        try {
            execute(sendMessage);
        }

        catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    public String putInString(List list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < list.size() + 1; i++)
            stringBuilder.append(i).append(". ").append(list.get(i-1).toString()).append("\n");

        return stringBuilder.toString();
    }
}