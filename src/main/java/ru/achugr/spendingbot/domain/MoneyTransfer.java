package ru.achugr.spendingbot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

/**
 * author: achugr
 * date: 30.01.16.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
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
}
