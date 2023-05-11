package com.example.telegrambot.services;

import com.example.telegrambot.config.BotConfig;
import com.example.telegrambot.entities.Actor;
import com.example.telegrambot.entities.Film;
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

    @Autowired
    public TelegramBot(BotConfig config, ActorService actorService, FilmService filmService) {
        this.config = config;
        this.actorService = actorService;
        this.filmService = filmService;
        List<BotCommand> commandList = new ArrayList<>();
        commandList.add(new BotCommand("/films", "фильмы"));
        commandList.add(new BotCommand("/actors", "актеры"));
        commandList.add(new BotCommand("/genres", "жанры"));
        commandList.add(new BotCommand("/myfilms", "понравившиеся фильмы"));
        commandList.add(new BotCommand("/myactors", "любимые актеры"));
        commandList.add(new BotCommand("/watchlist", "буду смотреть"));
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

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/films":
                    sendMessage(chatId, getFilms());
                    break;
                case "/actors":
                    sendMessage(chatId, getActors());
                    break;
                case "/genres":
                    sendMessage(chatId, filmService.getGenres());
                    break;
                case "myfilms":
                    sendMessage(chatId, "sorry, command was not recognized");
                    break;
                case "/watchlist":
                    sendMessage(chatId, "sorry, command was not recognized");
                    break;
                case "/myactors":
                    sendMessage(chatId, "sorry, command was not recognized");
                    break;
                default:
                    sendMessage(chatId, "sorry, command was not recognized");
                    break;
            }
        }
    }

    private void startCommandReceived(long chatId, String firstName) {
        String answer = EmojiParser.parseToUnicode("Hi, " + firstName + ", nice to meet you!" + " :blush:");
        log.info("Replied to user " + firstName);
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

    public String getFilms() {
        List<Film> films = filmService.findAllOrderByRating();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < films.size() + 1; i++)
            stringBuilder.append(i).append(". ").append(films.get(i-1).toString()).append("\n");

        return stringBuilder.toString();
    }

    public String getActors() {
        List<Actor> actors = actorService.findAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < actors.size() + 1; i++)
            stringBuilder.append(i).append(". ").append(actors.get(i-1).toString()).append("\n");

        return stringBuilder.toString();
    }

}