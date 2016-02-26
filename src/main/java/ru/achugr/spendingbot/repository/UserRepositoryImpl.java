package ru.achugr.spendingbot.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.achugr.spendingbot.domain.User;

import javax.sql.DataSource;

/**
 * author: achugr
 * date: 22.02.16
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void MoneyTransferRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void saveUser(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", user.getId());
        params.addValue("userName", user.getUserName());
        params.addValue("firstName", user.getFirstName());
        params.addValue("lastName", user.getLastName());
        jdbcTemplate.update("INSERT INTO t_user(id, user_name, first_name, last_name)" +
                "SELECT :id, :userName, :firstName, :lastName " +
                "WHERE NOT EXISTS ( SELECT * FROM t_user WHERE id=:id)", params);
    }
}
