package ru.achugr.spendingbot.testutil;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.achugr.spendingbot.domain.MoneyTransfer;
import ru.achugr.spendingbot.domain.OperationType;
import ru.achugr.spendingbot.domain.User;
import ru.achugr.spendingbot.repository.MoneyTransferRepository;
import ru.achugr.spendingbot.repository.UserRepository;

import java.math.BigDecimal;

/**
 * author: achugr
 * date: 06.02.16
 */
@Component
public class FillDbHelper {

    @Autowired
    MoneyTransferRepository moneyTransferRepository;

    @Autowired
    UserRepository userInfoRepository;

    public User createUser(Integer id, String userName) {
        User user = new User(id, userName);
        userInfoRepository.saveUser(user);
        return user;
    }

    public void spendMoney(@NotNull Long chatId, @NotNull Integer userId, @NotNull BigDecimal sum) {
        MoneyTransfer moneyTransfer = new MoneyTransfer(
                chatId,
                userId,
                OperationType.SPEND,
                sum
        );

        moneyTransferRepository.saveTransfer(moneyTransfer);
    }
}
