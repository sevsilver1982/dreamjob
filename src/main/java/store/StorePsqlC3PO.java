package store;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class StorePsqlC3PO {
    private static final ComboPooledDataSource POOL = new ComboPooledDataSource();

    private StorePsqlC3PO() {
    }

    static {
        Properties properties = new Properties();
        try {
            properties.load(StorePsqlC3PO.class.getClassLoader().getResourceAsStream("app.properties"));
            POOL.setDriverClass(properties.getProperty("jdbc.driver"));
            POOL.setJdbcUrl(properties.getProperty("datasource.url"));
            POOL.setUser(properties.getProperty("datasource.username"));
            POOL.setPassword(properties.getProperty("datasource.password"));
            POOL.setMinPoolSize(Integer.parseInt(properties.getProperty("datasource.minIdle")));
            POOL.setMaxPoolSize(Integer.parseInt(properties.getProperty("datasource.maxIdle")));
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