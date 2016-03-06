package ru.achugr.spendingbot.util;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

import static ru.achugr.spendingbot.util.Command.CommandType.NEW_SESSION;

/**
 * author: achugr
 * date: 21.02.16
 */

@Data
public class NewSessionCommand extends Command {

    @Nullable
    private String sessionName;

    public NewSessionCommand() {
        super(NEW_SESSION);
    }

    public NewSessionCommand(String sessionName) {
        super(NEW_SESSION);
        this.sessionName = sessionName;
    }
}
