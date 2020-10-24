package store;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class StorePsqlHikariCP {
    private HikariDataSource pool;

    private StorePsqlHikariCP() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("app.properties"));
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(properties.getProperty("jdbc.driver"));
            hikariConfig.setJdbcUrl(properties.getProperty("datasource.url"));
            hikariConfig.setUsername(properties.getProperty("datasource.username"));
            hikariConfig.setPassword(properties.getProperty("datasource.password"));
            hikariConfig.setMinimumIdle(Integer.parseInt(properties.getProperty("datasource.minIdle")));
            hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("datasource.maxIdle")));
            pool = new HikariDataSource(hikariConfig);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final class Props {
        private static final StorePsqlHikariCP INSTANCE = new StorePsqlHikariCP();
    }

    public static StorePsqlHikariCP getInstance() {
        return Props.INSTANCE;
    }

    public static Connection getConnection() {
        Connection connection;
        try {
            connection = getInstance().pool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}