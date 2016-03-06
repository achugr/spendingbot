package ru.achugr.spendingbot.util;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import ru.achugr.spendingbot.exception.WrongCommandException;

import java.util.Arrays;


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
        PAID("paid"), TOTAL("total"), NEW_SESSION("newsession"), UNKNOWN("unknown"), ERROR("error");

        private String name;

        CommandType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static CommandType getType(String typeName) {
        for (CommandType type : CommandType.values()) {
            if (type.getName().toLowerCase().equals(typeName.toLowerCase())) {
                return type;
            }
        }

        throw new WrongCommandException(String.format("Please, select one of %s commands",
                Arrays.toString(CommandType.values())));
    }
}