package ru.achugr.spendingbot.util;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import static ru.achugr.spendingbot.util.Command.CommandType.unknown;

/**
 * author: achugr
 * date: 21.02.16
 */
@Data
public class UnknownCommand extends Command {
    @NotNull
    private String message;

    public UnknownCommand(@NotNull String message) {
        super(unknown);
        this.message = message;
    }
}
