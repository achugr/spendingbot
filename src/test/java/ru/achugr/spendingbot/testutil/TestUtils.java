package ru.achugr.spendingbot.testutil;

import ru.achugr.spendingbot.dto.Total;
import ru.achugr.spendingbot.dto.TotalEntry;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author: achugr
 * date: 06.02.16
 */
public class TestUtils {

    public static Map<Integer, BigDecimal> convert(Total total) {
        return total.getTotalEntries().stream()
                .collect(Collectors.toMap(TotalEntry::getUserId, TotalEntry::getSum));
    }
}
