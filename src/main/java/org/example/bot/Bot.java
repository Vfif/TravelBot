package org.example.bot;

import org.example.model.City;
import org.example.repository.CityRepository;
import org.example.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Component
public class Bot extends TelegramLongPollingBot {
    @Autowired
    private CityService cityService;

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            Optional<City> city = cityService.findByName(message.getText());
            SendMessage sendMessage = new SendMessage();

            sendMessage.setText(city.isPresent()?city.get().getInfo():"City not found");

            sendMessage.setChatId(update.getMessage().getChatId());

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBotUsername() {
        return "TravelInfoBot";
    }

    public String getBotToken() {
        return "1309842660:AAHOkB09skvTYN3IYOVBEzlXO4vSu-S-T2c";
    }
}
