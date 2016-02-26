package ru.achugr.spendingbot.repository;

import ru.achugr.spendingbot.domain.MoneyTransfer;
import ru.achugr.spendingbot.dto.Total;

/**
 * author: achugr
 * date: 05.02.16
 */
public interface MoneyTransferRepository {
    void saveTransfer(MoneyTransfer moneyTransfer);

    Total getTotal(Long chatId);


}
