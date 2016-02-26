package ru.achugr.spendingbot.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

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
        if (url == null && dbName==null){
            log.error("Your spring.datasource connection pool configuration is incorrect! The application" +
                    "cannot start. Please check your Spring profile");

            throw new ApplicationContextException("spring.datasource connection pool is not configured correctly");
        }
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(dataSourceClassName);
        log.debug("Servername: {}, databaseName: {}, url: {}", serverName, dbName, url);
        if (url == null || "".equals(url)) {
            config.addDataSourceProperty("databaseName", dbName);
            config.addDataSourceProperty("serverName", serverName);
        } else {
            config.addDataSourceProperty("url", url);
        }
        config.addDataSourceProperty("user", userName);
        config.addDataSourceProperty("password", userPass);
        config.setMaximumPoolSize(maximumPoolSize);

        return new HikariDataSource(config);
    }
}
