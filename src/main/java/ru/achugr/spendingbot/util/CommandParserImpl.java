package ru.achugr.spendingbot.util;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.achugr.spendingbot.exception.WrongCommandException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

        return buildCommand(Optional.of(m.group(2)), commandType);
    }

    private Command buildCommand(Optional<String> argLine, Command.CommandType commandType) {
        Optional<List<String>> args = Optional.empty();
        if (argLine.isPresent()) {
            args = Optional.of(Arrays.asList(argLine.get().trim().split("\\s")));
        }
        switch (commandType) {
            case PAID:
                return parsePaidCommand(args);
            case TOTAL:
                return new TotalCommand();
            case NEW_SESSION:
                return parseNewSessionCommand(args);
            default:
                return notImplemented();
        }
    }

    @NotNull
    private Command parseNewSessionCommand(Optional<List<String>> args) {
        if (args.isPresent() && args.get().size() == 1) {
            return new NewSessionCommand(args.get().get(0));
        } else {
            return new NewSessionCommand();
        }
    }

    @NotNull
    private Command parsePaidCommand(Optional<List<String>> args) {
        if (args.isPresent() && args.get().size() == 1) {
            try {
                BigDecimal sum = new BigDecimal(args.get().get(0));

                if (sum.abs().compareTo(BigDecimal.valueOf(1000000000000L)) > 0) {
                    return new ErrorCommand("Sum is too big");
                }
                return new PaidCommand(new BigDecimal(args.get().get(0)));
            } catch (NumberFormatException e) {
                return new ErrorCommand("Paid sum must be a digit");
            }
        } else {
            return new ErrorCommand("Paid command must contain exactly one digit argument");
        }
    }

    @NotNull
    private ErrorCommand notImplemented() {
        return new ErrorCommand("Command not implemented");
    }
}
