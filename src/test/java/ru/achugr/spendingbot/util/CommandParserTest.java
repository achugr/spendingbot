package ru.achugr.spendingbot.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * author: achugr
 * date: 14.02.16
 */
@RunWith(Parameterized.class)
public class CommandParserTest {

    private CommandParserImpl commandParser = new CommandParserImpl();

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"/paid 100", new PaidCommand(BigDecimal.valueOf(100))},
                {"/total", new TotalCommand()}
        });
    }

    private String commandStr;
    private Command command;

    public CommandParserTest(String commandStr, Command command) {
        this.commandStr = commandStr;
        this.command = command;
    }

    @Test
    public void test() {
        assertThat(commandParser.parse(commandStr), is(equalTo(command)));
    }
}