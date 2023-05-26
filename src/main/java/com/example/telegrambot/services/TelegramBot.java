package com.example.telegrambot.services;

import com.example.telegrambot.config.BotConfig;
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

            if (messageText.contains("Film: ")) {
                sendMessage(chatId, filmService.getFilmsByName(messageText.replace("Film: ", "")));
            }

            else if (messageText.contains("Genre: ")) {
                sendMessage(chatId, filmService.getFilmsByGenre(messageText.replace("Genre: ", "")));
            }

            else if (messageText.contains("Film director: ")) {
                sendMessage(chatId, filmService.getFilmsByFilmDirector(messageText.replace("Film director: ", "")));
            }

            else if (messageText.contains("Country: ")) {
                sendMessage(chatId, filmService.getFilmsByCountry(messageText.replace("Country: ", "")));
            }

            else {
                switch (messageText) {
                    case "/start":
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;
                    case "/films":
                        sendMessage(chatId, filmService.getFilms());
                        break;
                    case "/actors":
                        sendMessage(chatId, actorService.getActors());
                        break;
                    case "/genres":
                        sendMessage(chatId, filmService.getGenres());
                        break;
                    default:
                        sendMessage(chatId, "sorry, command was not recognized");
                        break;
                }
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

}