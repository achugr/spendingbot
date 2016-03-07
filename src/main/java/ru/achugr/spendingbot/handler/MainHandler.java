package ru.achugr.spendingbot.handler;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import ru.achugr.spendingbot.config.TelegramConfig;
import ru.achugr.spendingbot.repository.MoneyTransferRepositoryImpl;
import ru.achugr.spendingbot.service.MessageProcessingService;
import ru.achugr.spendingbot.util.*;

/**
 * author: achugr
 * date: 30.01.16
 */

@Slf4j
@Component("mainHandler")
public class MainHandler extends TelegramLongPollingBot {

    @Autowired
    private MoneyTransferRepositoryImpl moneyTransferRepository;

    @Autowired
    private CommandParserImpl commandParser;

    @Autowired
    private MessageProcessingService messageProcessingService;

    @Autowired
    private TelegramConfig telegramConfig;

    public MainHandler() {
        super();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                handleIncomingMessage(message);
            }
        }
    }

    private void handleIncomingMessage(Message message) {
        log.debug("Message accepted: " + message.getText());

        try {
            sendMessage(getResponseMessage(message));
        } catch (TelegramApiException e) {
            log.error("Send message failed", e);
        }
    }

    @NotNull
    private SendMessage getResponseMessage(Message message) {
        Command command = commandParser.parse(message.getText());
        log.debug("Chat id: " + message.getChatId());
        switch (command.getCommand()) {
            case PAID:
                return messageProcessingService.processPaidMessage((PaidCommand) command, message);
            case TOTAL:
                return messageProcessingService.processTotalMessage((TotalCommand) command, message);
            case NEW_SESSION:
                return messageProcessingService.processNewSessionMessage((NewSessionCommand) command, message);
            case UNKNOWN:
                return messageProcessingService.replyWithText(message, ((UnknownCommand) command).getMessage());
            case ERROR:
                return messageProcessingService.replyWithText(message, ((ErrorCommand) command).getMessage());
            default:
                return messageProcessingService.replyWithText(message, "Unknown command");
        }
    }

    @Override
    public String getBotUsername() {
        return telegramConfig.getTelegramBotName();
    }

    @Override
    public String getBotToken() {
        return telegramConfig.getTelegramToken();
    }
}
