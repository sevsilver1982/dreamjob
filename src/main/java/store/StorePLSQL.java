package store;

import model.ItemImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class StorePLSQL<T extends ItemImpl> implements StoreImpl<T>, AutoCloseable {
    private static final String APP_PROPERTIES = "app.properties";
    public static final String PROPERTY_JDBC_DRIVER = "jdbc.driver";
    public static final String PROPERTY_JDBC_URL = "jdbc.url";
    public static final String PROPERTY_JDBC_USERNAME = "jdbc.username";
    public static final String PROPERTY_JDBC_PASSWORD = "jdbc.password";

    private final Properties properties;
    private final Connection connection;

    public StorePLSQL() {
        properties = new Properties();
        try {
            properties.load(StorePLSQL.class.getClassLoader().getResourceAsStream(APP_PROPERTIES));
            connection = connect(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Connection connect(Properties properties) throws SQLException, ClassNotFoundException {
        Class.forName(properties.getProperty(PROPERTY_JDBC_DRIVER));
        return DriverManager.getConnection(
                properties.getProperty(PROPERTY_JDBC_URL),
                properties.getProperty(PROPERTY_JDBC_USERNAME),
                properties.getProperty(PROPERTY_JDBC_PASSWORD)
        );
    }

    public Properties getProperties() {
        return properties;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

}