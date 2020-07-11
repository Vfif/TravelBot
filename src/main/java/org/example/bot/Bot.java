package org.example.bot;

import lombok.extern.slf4j.Slf4j;
import org.example.model.City;
import org.example.service.CityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Slf4j
@Component
@PropertySource("classpath:application.properties")
public class Bot extends TelegramLongPollingBot {
    private static final String NO_INFORMATION_ABOUT_THIS_CITY = "No information about this city";
    private CityService cityService;

    @Value("${bot.username}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    public Bot(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            City city = cityService.findByName(message.getText())
                                    .orElse(City.builder()
                                            .info(NO_INFORMATION_ABOUT_THIS_CITY)
                                            .build());

            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(city.getInfo());
            sendMessage.setChatId(message.getChatId());

            try {
                execute(sendMessage);
                log.info("Send message: " + sendMessage.getText());
            } catch (TelegramApiException e) {
                log.error("Cannot send message", e);
            }
        }
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }
}
