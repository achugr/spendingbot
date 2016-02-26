package ru.achugr.spendingbot.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.achugr.spendingbot.dto.Total;

/**
 * author: achugr
 * date: 05.02.16
 */
public interface TotalEvaluatorService {
    Total getTotal(Long chatId);
}
