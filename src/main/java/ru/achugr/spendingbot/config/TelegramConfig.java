package ru.achugr.spendingbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * author: achugr
 * date: 30.01.16.
 */
@Component
public class TelegramConfig {
    //    TODO move it to secret place (but how with heroku?!)
    @Value("${telegram.token}")
    private String telegramToken;

    @Value("${telegram.botName}")
    private String telegramBotName;

    @PostConstruct
    public void initFromEnvVars() {
        if (System.getenv("SPBOT_NAME") != null) {
            telegramBotName = System.getenv("SPBOT_NAME");
            telegramToken = System.getenv("SPBOT_TOKEN");
        }
    }

    public String getTelegramToken() {
        return telegramToken;
    }

    public String getTelegramBotName() {
        return telegramBotName;
    }
}

