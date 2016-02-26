package ru.achugr.spendingbot.util;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;

import static ru.achugr.spendingbot.util.Command.CommandType.paid;

/**
 * author: achugr
 * date: 14.02.16
 */
@Data
public class PaidCommand extends Command {

    @NotNull
    private BigDecimal sum;

    public PaidCommand(@NotNull BigDecimal sum) {
        super(paid);
        this.sum = sum;
    }

}
