package ru.achugr.spendingbot.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.achugr.spendingbot.Application;
import ru.achugr.spendingbot.domain.User;

/**
 * author: achugr
 * date: 26.02.16
 */

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringApplicationConfiguration(classes = Application.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreate(){
        User user = new User(1, "achugr", "a", "chugr");
        userRepository.saveUser(user);
        userRepository.saveUser(user);
    }
}
