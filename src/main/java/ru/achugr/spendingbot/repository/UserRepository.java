package ru.achugr.spendingbot.repository;

import ru.achugr.spendingbot.domain.User;

/**
 * author: achugr
 * date: 22.02.16
 */
public interface UserRepository {

    void saveUser(User user);
}
