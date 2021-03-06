package ru.achugr.spendingbot.util;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

import static ru.achugr.spendingbot.util.Command.CommandType.PAID;

/**
 * author: achugr
 * date: 14.02.16
 */
@Data
public class PaidCommand extends Command {

    @NotNull
    private BigDecimal sum;

    public PaidCommand(@NotNull BigDecimal sum) {
        super(PAID);
        this.sum = sum;
    }

}
