package ru.achugr.spendingbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

/**
 * author: achugr
 * date: date: 31.01.16
 */
@Slf4j
@SpringBootApplication
@ComponentScan(includeFilters =
        {
                @ComponentScan.Filter(pattern = "ru.achugr.repository.*", type = FilterType.REGEX),
                @ComponentScan.Filter(pattern = "ru.achugr.handler.*", type = FilterType.REGEX),
        }
)
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot((TelegramLongPollingBot) ctx.getBean("mainHandler"));
        } catch (TelegramApiException e) {
            log.error("Something went wrong", e);
        }
    }
//    public static void main(String[] args) {
//        AnnotationConfigApplicationContext ctx =
//                new AnnotationConfigApplicationContext();
//
//        ctx.register(DbConfig.class);
//
//        ctx.scan("ru.achugr.spendingbot.handler");
//        ctx.scan("ru.achugr.spendingbot.repository");
//        ctx.refresh();
//        log.info("Starting SpendingBot");
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
//        try {
//            telegramBotsApi.registerBot((TelegramLongPollingBot) ctx.getBean("mainHandler"));
//        } catch (TelegramApiException e) {
//            log.error("Something went wrong", e);
//        }
//    }
}
