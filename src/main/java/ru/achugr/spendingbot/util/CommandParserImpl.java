package ru.achugr.spendingbot.util;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.achugr.spendingbot.exception.WrongCommandException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * author: achugr
 * date: 14.02.16
 */
@Component
@Slf4j
public class CommandParserImpl implements CommandParser {

    @NotNull
    private Pattern cmdRegexp;

    {
        cmdRegexp = Pattern.compile(String.format("/(%s)(.+)?",
                Arrays.asList(Command.CommandType.values()).stream()
                        .map(command -> command.getName().toLowerCase())
                        .collect(Collectors.joining("|"))));
    }

    public Command parse(@NotNull String commandStr) {
        String preparedCommandStr = commandStr.toLowerCase();
        Matcher m = cmdRegexp.matcher(preparedCommandStr);
        if (m.matches()) {
            log.debug(String.format("String %s matches command regexp %s", preparedCommandStr, cmdRegexp.toString()));
            return buildCommand(m);
        } else {
            log.debug(String.format("String %s doesn't matches command regexp %s", preparedCommandStr, cmdRegexp.toString()));
            throw new WrongCommandException(String.format("Can't parse command string %s", preparedCommandStr));
        }
    }

    private Command buildCommand(@NotNull Matcher m) {
        if (m.group(1) == null) {
            return new UnknownCommand("Command must be like '/commandName arg1 arg2 ...'");
        }

        Command.CommandType commandType = Command.getType(m.group(1));

        if (m.group(2) == null) {
            return buildNoArgsCommand(commandType);
        } else {
            return buildCommand(m, commandType);
        }
    }

    private Command buildCommand(@NotNull Matcher m, Command.CommandType commandType) {
        List<String> args = Arrays.asList(m.group(2).trim().split("\\s"));
        switch (commandType) {
            case PAID:
                if (args.size() == 1) {
                    return new PaidCommand(new BigDecimal(args.get(0)));
                } else {
                    return new ErrorCommand("Paid command should contain only one arg");
                }
            case NEW_SESSION:
                if (args.size() == 1) {
                    return new NewSessionCommand(args.get(0));
                } else {
                    return new NewSessionCommand();
                }
            default:
                return notImplemented();
        }
    }

    @NotNull
    private ErrorCommand notImplemented() {
        return new ErrorCommand("Command not implemented");
    }

    private Command buildNoArgsCommand(Command.CommandType commandType) {
        switch (commandType) {
            case TOTAL:
                return new TotalCommand();
            case NEW_SESSION:
                return new NewSessionCommand();
            default:
                return notImplemented();
        }
    }

}
