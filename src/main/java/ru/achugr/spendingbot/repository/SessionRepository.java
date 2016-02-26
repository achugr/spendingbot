package ru.achugr.spendingbot.repository;

import ru.achugr.spendingbot.domain.Session;

/**
 * author: achugr
 * date: 22.02.16
 */
public interface SessionRepository {
    Session createNew(Long chatId, String name);
}
