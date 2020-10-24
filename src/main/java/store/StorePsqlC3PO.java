package store;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class StorePsqlC3PO {
    private static final Logger LOGGER = Logger.getLogger(StorePsqlC3PO.class);
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
            LOGGER.debug(e.getMessage());
        }
    }

    private static final class Props {
        private static final StorePsqlC3PO INSTANCE = new StorePsqlC3PO();
    }

    public static StorePsqlC3PO getInstance() {
        return Props.INSTANCE;
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = getInstance().pool.getConnection();
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
        }
        return connection;
    }

}