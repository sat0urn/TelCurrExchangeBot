package org.talos.telbotbank.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.talos.telbotbank.model.CurrencyModel;
import org.talos.telbotbank.service.CurrencyService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.text.ParseException;

@Component
public class CurrencyExchangeBot extends TelegramLongPollingBot {

    public CurrencyExchangeBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public String getBotUsername() {
        return System.getenv("TG_USERNAME");
    }

    @Override
    public void onUpdateReceived(Update update) {
        CurrencyModel currencyModel = new CurrencyModel();
        String currency = "";

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start" -> startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                default -> {
                    try {
                        currency = CurrencyService.getCurrencyRate(messageText, currencyModel);
                    } catch (IOException e) {
                        sendMessage(chatId,
                                """
                                        We have not found such a currency.
                                        Enter the currency whose official exchange rate
                                        you want to know in relation to BYN.
                                        For example: USD
                                        """);
                    } catch (ParseException e) {
                        throw new RuntimeException("Unable to parse date");
                    }
                    sendMessage(chatId, currency);
                }
            }
        }
    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = String.format("""
                Hi, %s, nice to meet you!
                Enter the currency whose official exchange rate
                you want to know in relation to BYN.
                For example: USD
                """, name);
        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(String.valueOf(chatId))
                .text(textToSend)
                .build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException ignored) {
        }
    }
}
