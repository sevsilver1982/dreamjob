package store;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class StorePsqlHikariCP {
    private static final HikariDataSource POOL;

    private StorePsqlHikariCP() {
    }

    static {
        Properties properties = new Properties();
        try {
            properties.load(StorePsqlHikariCP.class.getClassLoader().getResourceAsStream("app.properties"));
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(properties.getProperty("jdbc.driver"));
            hikariConfig.setJdbcUrl(properties.getProperty("datasource.url"));
            hikariConfig.setUsername(properties.getProperty("datasource.username"));
            hikariConfig.setPassword(properties.getProperty("datasource.password"));
            hikariConfig.setMinimumIdle(Integer.parseInt(properties.getProperty("datasource.minIdle")));
            hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("datasource.maxIdle")));
            POOL = new HikariDataSource(hikariConfig);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        Connection connection;
        try {
            connection = POOL.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}