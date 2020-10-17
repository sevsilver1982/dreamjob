package store;

import model.DBProperties;
import model.ItemImpl;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class StoreDBImpl<T extends ItemImpl> extends DBProperties implements Store<T> {
    private static final Logger LOGGER = Logger.getLogger(DBProperties.class);
    private static final DBProperties INSTANCE = DBProperties.getDBProperty();
    private static Connection connection = null;

    public Connection getConnection() {
        try {
            if (connection == null) {
                Class.forName(INSTANCE.getDriver());
                connection = DriverManager.getConnection(INSTANCE.getUrl(), INSTANCE.getUsername(), INSTANCE.getPassword());
            }
        } catch (Exception e) {
            LOGGER.debug(e.getStackTrace());
        }
        return connection;
    }

}