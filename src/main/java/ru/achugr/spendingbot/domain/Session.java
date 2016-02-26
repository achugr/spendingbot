package ru.achugr.spendingbot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * author: achugr
 * date: 22.02.16
 */

@Data
@AllArgsConstructor
public class Session {

    @NotNull
    private Long id;

    @NotNull
    private Long chatId;

    @NotNull
    private Integer sessionNumber;

    private String sessionName;
}
