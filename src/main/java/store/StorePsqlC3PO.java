package store;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class StorePsqlC3PO {
    private static final ComboPooledDataSource POOL = new ComboPooledDataSource();

    private StorePsqlC3PO() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("app.properties"));
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

    private static final class Props {
        private static final StorePsqlC3PO INSTANCE = new StorePsqlC3PO();
    }

    public static StorePsqlC3PO getInstance() {
        return Props.INSTANCE;
    }

    public static Connection getConnection() {
        Connection connection;
        try {
            connection = getInstance().POOL.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}