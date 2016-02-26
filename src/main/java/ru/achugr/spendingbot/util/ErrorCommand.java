package ru.achugr.spendingbot.util;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.omg.CORBA.portable.Streamable;

import static ru.achugr.spendingbot.util.Command.CommandType.error;

/**
 * author: achugr
 * date: 21.02.16
 */
@Data
public class ErrorCommand extends Command {
    @NotNull
    private String message;

    public ErrorCommand(String message) {
        super(error);
        this.message = message;
    }
}
