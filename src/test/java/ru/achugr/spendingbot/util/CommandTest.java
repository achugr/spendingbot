package ru.achugr.spendingbot.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * author: achugr
 * date: 06.03.16
 */

@RunWith(Parameterized.class)
public class CommandTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"paid", Command.CommandType.PAID},
                {"total", Command.CommandType.TOTAL},
                {"newsession", Command.CommandType.NEW_SESSION}
        });
    }

    private String commandStr;
    private Command.CommandType commandType;

    public CommandTest(String commandStr, Command.CommandType commandType) {
        this.commandStr = commandStr;
        this.commandType = commandType;
    }

    @Test
    public void test() {
        assertThat(Command.getType(commandStr), is(equalTo(commandType)));
    }

}
