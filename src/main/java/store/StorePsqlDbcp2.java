package store;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class StorePsqlDbcp2 {
    private static final BasicDataSource POOL = new BasicDataSource();

    private StorePsqlDbcp2() {
    }

    static {
        Properties properties = new Properties();
        try {
            properties.load(StorePsqlDbcp2.class.getClassLoader().getResourceAsStream("app.properties"));
            POOL.setDriverClassName(properties.getProperty("jdbc.driver"));
            POOL.setUrl(properties.getProperty("datasource.url"));
            POOL.setUsername(properties.getProperty("datasource.username"));
            POOL.setPassword(properties.getProperty("datasource.password"));
            POOL.setMinIdle(Integer.parseInt(properties.getProperty("datasource.minIdle")));
            POOL.setMaxIdle(Integer.parseInt(properties.getProperty("datasource.maxIdle")));
            POOL.setMaxOpenPreparedStatements(100);
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