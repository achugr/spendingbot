package ru.achugr.spendingbot.repository;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.achugr.spendingbot.domain.MoneyTransfer;
import ru.achugr.spendingbot.dto.Total;
import ru.achugr.spendingbot.dto.TotalEntry;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author: achugr
 * date: 30.01.16
 */

@Slf4j
@Repository
public class MoneyTransferRepositoryImpl implements MoneyTransferRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    public void MoneyTransferRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void saveTransfer(@NotNull MoneyTransfer moneyTransfer) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("chatId", moneyTransfer.getChatId());
        params.addValue("userId", moneyTransfer.getUserId());
        params.addValue("operationType", moneyTransfer.getOperationType().toString());
        params.addValue("sum", moneyTransfer.getSum());
        insertMoneyTransfer(params);
    }


    private void insertMoneyTransfer(MapSqlParameterSource params) {
        jdbcTemplate.update("" +
                "WITH last_session_id AS (\n" +
                "    SELECT ts.id\n" +
                "    FROM t_session AS ts\n" +
                "    WHERE ts.chat_id = :chatId\n" +
                "    ORDER BY ts.session_number DESC\n" +
                "    LIMIT 1\n" +
                "),\n" +
                "    create_session_if_needed AS (\n" +
                "    INSERT INTO t_session (chat_id, session_number, session_name) SELECT\n" +
                "                                                                    :chatId,\n" +
                "                                                                    0,\n" +
                "                                                                    'initial session'\n" +
                "                                                                  WHERE NOT exists(SELECT *\n" +
                "                                                                                   FROM last_session_id)\n" +
                "    RETURNING id\n" +
                "  )\n" +
                "INSERT INTO t_money_transfer (session_id, user_id, operation_type, sum)\n" +
                "  SELECT\n" +
                "    id,\n" +
                "    :userId,\n" +
                "    :operationType,\n" +
                "    :sum\n" +
                "  FROM last_session_id\n" +
                "  UNION ALL SELECT\n" +
                "              id,\n" +
                "              :userId,\n" +
                "              :operationType,\n" +
                "              :sum\n" +
                "            FROM create_session_if_needed\n" +
                "            WHERE id IS NOT NULL", params);
    }

    @Override
    public Total getTotal(@NotNull Long chatId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("chatId", chatId);
        Total total = new Total();
        List<TotalEntry> entriesList = jdbcTemplate.queryForList(TOTAL_SUM_SQL, params).stream().map(
                row -> {
//                    TODO do separate query
                    total.setTotalSum(new BigDecimal(row.get("total_sum").toString()));
                    return new TotalEntry(
                            Integer.valueOf(row.get("user_id").toString()),
                            row.get("user_name").toString(),
                            new BigDecimal(row.get("user_total_sum").toString())
                    );
                }
        ).collect(Collectors.toList());

        total.setTotalEntries(entriesList);
        return total;
    }


    private static final String TOTAL_SUM_SQL = "WITH\n" +
            "    session_id AS (\n" +
            "      SELECT id\n" +
            "      FROM t_session\n" +
            "      WHERE chat_id = :chatId\n" +
            "      ORDER BY session_number DESC\n" +
            "      LIMIT 1\n" +
            "  ),\n" +
            "    total_sum AS (SELECT sum(sum)\n" +
            "                  FROM t_money_transfer\n" +
            "                  WHERE session_id = (SELECT *\n" +
            "                                      FROM session_id)),\n" +
            "    total_users AS (SELECT count(DISTINCT user_id)\n" +
            "                    FROM t_money_transfer\n" +
            "                    WHERE session_id = (SELECT *\n" +
            "                                        FROM session_id))\n" +
            "SELECT\n" +
            "  res.user_id                                                       AS user_id,\n" +
            "  CASE WHEN tu.user_name IS NOT NULL\n" +
            "    THEN tu.user_name\n" +
            "  ELSE CASE WHEN tu.first_name IS NOT NULL\n" +
            "    THEN (tu.first_name || ' ' || COALESCE(tu.last_name, ''))\n" +
            "       END END                                                      AS user_name,\n" +
            "  CAST(res.user_sum - res.total_sum / user_count AS NUMERIC(16, 2)) AS user_total_sum,\n" +
            "  CAST(res.total_sum AS NUMERIC(16, 2))                             AS total_sum\n" +
            "FROM\n" +
            "  (SELECT\n" +
            "     (\n" +
            "       SELECT *\n" +
            "       FROM total_users\n" +
            "       LIMIT 1\n" +
            "     )            AS user_count,\n" +
            "     (\n" +
            "       SELECT *\n" +
            "       FROM total_sum\n" +
            "       LIMIT 1\n" +
            "     )            AS total_sum,\n" +
            "     tmt.user_id  AS user_id,\n" +
            "     sum(tmt.sum) AS user_sum\n" +
            "   FROM t_money_transfer AS tmt\n" +
            "     INNER JOIN t_user AS tu ON tu.id = tmt.user_id\n" +
            "   WHERE session_id = (SELECT *\n" +
            "                       FROM session_id)\n" +
            "   GROUP BY user_id, user_name\n" +
            "  ) AS res\n" +
            "  INNER JOIN t_user AS tu ON res.user_id = tu.id;";

}
