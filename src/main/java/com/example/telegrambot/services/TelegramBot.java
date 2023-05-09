package com.example.telegrambot.services;

import com.example.telegrambot.config.BotConfig;
import com.example.telegrambot.entities.Actor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
                case "актеры":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    sendMessage(chatId, "sorry, command was not recognized");
                    break;
            }
        }
    }

    private void startCommandReceived(long chatId, String firstName) {
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 1;
        for (Actor actor : actorService.findAll()) {
            stringBuilder.append(counter + ". " + actor.toString() + "\n");
            counter++;
        }
        log.info("Replied to user " + firstName);

        sendMessage(chatId, stringBuilder.toString());
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