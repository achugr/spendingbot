package ru.achugr.spendingbot.util;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;


/**
 * author: achugr
 * date: 14.02.16
 */
@Data
public abstract class Command {
    @NotNull
    protected CommandType command;

    protected Command(@NotNull CommandType command) {
        this.command = command;
    }

    public enum CommandType {
        paid, total, newSession, unknown, error
    }
}
