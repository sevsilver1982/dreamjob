package store;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class StorePsqlC3PO {
    private final ComboPooledDataSource pool = new ComboPooledDataSource();

    private StorePsqlC3PO() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("app.properties"));
            pool.setDriverClass(properties.getProperty("jdbc.driver"));
            pool.setJdbcUrl(properties.getProperty("datasource.url"));
            pool.setUser(properties.getProperty("datasource.username"));
            pool.setPassword(properties.getProperty("datasource.password"));
            pool.setMinPoolSize(Integer.parseInt(properties.getProperty("datasource.minIdle")));
            pool.setMaxPoolSize(Integer.parseInt(properties.getProperty("datasource.maxIdle")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final class Props {
        private static final StorePsqlC3PO INSTANCE = new StorePsqlC3PO();
    }

    public static StorePsqlC3PO getInstance() {
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