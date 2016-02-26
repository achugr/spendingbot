package ru.achugr.spendingbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * author: achugr
 * date: 05.02.16
 */
@Data
@AllArgsConstructor
public class TotalEntry {

    @NotNull
    private Integer userId;
    @NotNull
    private String userName;
    @NotNull
    private BigDecimal sum;

    @Override
    public String toString() {
        return String.format("User %s should pay %s", userName, sum);
    }
}
