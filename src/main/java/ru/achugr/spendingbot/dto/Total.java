package ru.achugr.spendingbot.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * author: achugr
 * date: 05.02.16
 */
@Data
public class Total {
    private BigDecimal totalSum;
    private List<TotalEntry> totalEntries = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Total sum: %s", totalSum));
        totalEntries.forEach(e -> sb.append("\n").append(e.toString()));
        return sb.toString();
    }
}
