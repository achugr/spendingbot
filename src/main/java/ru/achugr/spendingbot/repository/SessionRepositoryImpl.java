package ru.achugr.spendingbot.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.achugr.spendingbot.domain.Session;

import javax.sql.DataSource;

/**
 * author: achugr
 * date: 22.02.16
 */
@Repository
public class SessionRepositoryImpl implements SessionRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert sessionInsert;

    @Autowired
    public void MoneyTransferRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.sessionInsert = new SimpleJdbcInsert(dataSource).withTableName("t_session").usingGeneratedKeyColumns("id", "session_number");
    }

    @Override
    public Session createNew(Long chatId, String name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("chatId", chatId);
        params.addValue("sessionName", name);
        KeyHolder keyHolder = sessionInsert.executeAndReturnKeyHolder(params);
        Long sessionId = Long.parseLong(keyHolder.getKeys().get("id").toString());
        Integer sessionNumber = Integer.parseInt(keyHolder.getKeys().get("session_number").toString());
        return new Session(sessionId, chatId, sessionNumber, name);
    }
}
