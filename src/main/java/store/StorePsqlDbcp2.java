package store;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class StorePsqlDbcp2 {
    private final BasicDataSource pool = new BasicDataSource();

    private StorePsqlDbcp2() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("app.properties"));
            pool.setDriverClassName(properties.getProperty("jdbc.driver"));
            pool.setUrl(properties.getProperty("datasource.url"));
            pool.setUsername(properties.getProperty("datasource.username"));
            pool.setPassword(properties.getProperty("datasource.password"));
            pool.setMinIdle(Integer.parseInt(properties.getProperty("datasource.minIdle")));
            pool.setMaxIdle(Integer.parseInt(properties.getProperty("datasource.maxIdle")));
            pool.setMaxOpenPreparedStatements(100);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final class Props {
        private static final StorePsqlDbcp2 INSTANCE = new StorePsqlDbcp2();
    }

    public static StorePsqlDbcp2 getInstance() {
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