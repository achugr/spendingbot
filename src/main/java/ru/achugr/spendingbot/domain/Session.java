package ru.achugr.spendingbot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * author: achugr
 * date: 22.02.16
 */

@Getter
public class Session {

    @NotNull
    private final Long id;

    @NotNull
    private final Long chatId;

    @NotNull
    private final Integer sessionNumber;

    private String sessionName;

    public Session(@NotNull Long id, @NotNull Long chatId, @NotNull Integer sessionNumber) {
        this.id = id;
        this.chatId = chatId;
        this.sessionNumber = sessionNumber;
    }

    public Session(@NotNull Long id, @NotNull Long chatId, @NotNull Integer sessionNumber, String sessionName) {
        this.id = id;
        this.chatId = chatId;
        this.sessionNumber = sessionNumber;
        this.sessionName = sessionName;
    }
}
