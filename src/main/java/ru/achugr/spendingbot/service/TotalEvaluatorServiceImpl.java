package ru.achugr.spendingbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.achugr.spendingbot.dto.Total;
import ru.achugr.spendingbot.repository.MoneyTransferRepositoryImpl;

/**
 * author: achugr
 * date: 05.02.16
 */
@Service
public class TotalEvaluatorServiceImpl implements TotalEvaluatorService {

    @Autowired
    private MoneyTransferRepositoryImpl moneyTransferRepository;

    @Override
    public Total getTotal(Long chatId) {
        return moneyTransferRepository.getTotal(chatId);
    }
}
