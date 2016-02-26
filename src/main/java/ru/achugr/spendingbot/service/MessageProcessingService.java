package ru.achugr.spendingbot.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import ru.achugr.spendingbot.domain.MoneyTransfer;
import ru.achugr.spendingbot.domain.Session;
import ru.achugr.spendingbot.domain.User;
import ru.achugr.spendingbot.dto.Total;
import ru.achugr.spendingbot.repository.MoneyTransferRepository;
import ru.achugr.spendingbot.repository.SessionRepository;
import ru.achugr.spendingbot.repository.UserRepository;
import ru.achugr.spendingbot.util.NewSessionCommand;
import ru.achugr.spendingbot.util.PaidCommand;
import ru.achugr.spendingbot.util.TotalCommand;

import static ru.achugr.spendingbot.domain.OperationType.SPEND;

/**
 * author: achugr
 * date: 14.02.16
 */
@Slf4j
@Service
public class MessageProcessingService {

    @Autowired
    private MoneyTransferRepository moneyTransferRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @NotNull
    public SendMessage processPaidMessage(@NotNull PaidCommand command, @NotNull Message message) {
        log.debug(String.format("User %s spend %s", message.getFrom().getId(), command.getSum()));
        MoneyTransfer moneyTransfer = new MoneyTransfer(
                message.getChatId(),
                message.getFrom().getId(),
                SPEND,
                command.getSum()
        );

        org.telegram.telegrambots.api.objects.User user = message.getFrom();
        userRepository.saveUser(new User(user.getId(), user.getUserName(), user.getFirstName(), user.getLastName()));
        moneyTransferRepository.saveTransfer(moneyTransfer);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(String.format("%s paid %s", message.getFrom().getFirstName(), command.getSum()));
        return sendMessage;
    }

    @NotNull
    public SendMessage processNewSessionMessage(@NotNull NewSessionCommand command, @NotNull Message message) {
        log.debug(String.format("User %s requested new session", message.getFrom().getId()));

        Session session = sessionRepository.createNew(message.getChatId(), command.getSessionName());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(String.format("New session [%s] started",
                session.getSessionName() != null ? session.getSessionName() : session.getSessionNumber()));
        return sendMessage;
    }

    @NotNull
    public SendMessage processTotalMessage(@NotNull TotalCommand command, @NotNull Message message) {
        log.debug(String.format("User %s requested total", message.getFrom().getId()));
        Total total = moneyTransferRepository.getTotal(message.getChatId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(total.toString());
        return sendMessage;
    }

    @NotNull
    public SendMessage replyWithText(@NotNull Message message, @NotNull String errorText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(errorText);
        return sendMessage;
    }

}
