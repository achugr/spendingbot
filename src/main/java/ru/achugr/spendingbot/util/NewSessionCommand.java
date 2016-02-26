package ru.achugr.spendingbot.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.Null;

import static ru.achugr.spendingbot.util.Command.CommandType.newSession;

/**
 * author: achugr
 * date: 21.02.16
 */

@Data
public class NewSessionCommand extends Command {

    @Nullable
    private String sessionName;

    public NewSessionCommand() {
        super(newSession);
    }

    public NewSessionCommand(String sessionName) {
        super(newSession);
        this.sessionName = sessionName;
    }
}
