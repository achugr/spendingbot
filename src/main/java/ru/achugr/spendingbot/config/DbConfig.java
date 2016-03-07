package ru.achugr.spendingbot.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * author: achugr
 * date: 30.01.16.
 */
@Configuration
@Slf4j
public class DbConfig {

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String userPass;

    @Value("${spring.datasource.driver}")
    private String driver;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.databaseName}")
    private String dbName;

    @Value("${spring.datasource.dataSourceClassName}")
    private String dataSourceClassName;

    @Value("${spring.datasource.maximumPoolSize}")
    private Integer maximumPoolSize;

    @Value("${spring.datasource.serverName}")
    private String serverName;

    @Bean
    public DataSource dataSource() {
        log.debug("Configuring Datasource");
        HikariConfig config = new HikariConfig();
        if (System.getenv("SPBOT_DB_URL") != null) {
            configureWithEnvVars(config);
        } else {
            configureWithFileConfig(config);
        }

        return new HikariDataSource(config);
    }

    private void configureWithFileConfig(HikariConfig config) {
        if (url == null && dbName == null) {
            log.error("Your spring.datasource connection pool configuration is incorrect! The application" +
                    "cannot start. Please check your Spring profile");

            throw new ApplicationContextException("spring.datasource connection pool is not configured correctly");
        }
        config.setDataSourceClassName(dataSourceClassName);

        if (url == null || "".equals(url)) {
            config.addDataSourceProperty("databaseName", dbName);
            config.addDataSourceProperty("serverName", serverName);
        } else {
            config.addDataSourceProperty("url", url);
        }
        config.addDataSourceProperty("user", userName);
        config.addDataSourceProperty("password", userPass);
        config.setMaximumPoolSize(maximumPoolSize);

        log.debug("Servername: {}, databaseName: {}, url: {}", serverName, dbName, url);
    }

    private void configureWithEnvVars(HikariConfig config) {
        config.addDataSourceProperty("url", System.getenv("SPBOT_DB_URL"));
        config.addDataSourceProperty("user", System.getenv("SPBOT_DB_USER"));
        config.addDataSourceProperty("password", System.getenv("SPBOT_DB_PASS"));

        config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
    }

}
