package ru.achugr.spendingbot.domain;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

/**
 * author: achugr
 * date: 30.01.16.
 */
@Getter
public class MoneyTransfer {

    //    чтобы использовать бота в одном и том же чате для разных целей
    @NotNull
    private final Long chatId;

    @Nullable
    private Integer sessionNumber;

    @NotNull
    private final Integer userId;

    @NotNull
    private final OperationType operationType;

    @NotNull
    private final BigDecimal sum;

    public MoneyTransfer(@NotNull Long chatId, @NotNull Integer userId, @NotNull OperationType operationType, @NotNull BigDecimal sum) {
        this.chatId = chatId;
        this.userId = userId;
        this.operationType = operationType;
        this.sum = sum;
    }

    public MoneyTransfer(@NotNull Long chatId, @NotNull Integer userId, @NotNull OperationType operationType, @NotNull BigDecimal sum, Integer sessionNumber) {
        this.chatId = chatId;
        this.userId = userId;
        this.operationType = operationType;
        this.sum = sum;
        this.sessionNumber = sessionNumber;
    }
}
