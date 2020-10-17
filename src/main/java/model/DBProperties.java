package model;

import org.apache.log4j.Logger;

import java.util.Properties;

public class DBProperties {
    private static final Logger LOGGER = Logger.getLogger(DBProperties.class);
    private static final DBProperties INSTANCE = new DBProperties();

    private String driver;
    private String url;
    private String username;
    private String password;

    public DBProperties() {
        load();
    }

    public void load() {
        try {
            Properties properties = new Properties();
            properties.load(DBProperties.class.getClassLoader().getResourceAsStream("app.properties"));
            driver = properties.getProperty("jdbc.driver");
            url = properties.getProperty("jdbc.url");
            username = properties.getProperty("jdbc.username");
            password = properties.getProperty("jdbc.password");
        } catch (Exception e) {
            LOGGER.debug(e.getStackTrace());
        }
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static DBProperties getDBProperty() {
        return INSTANCE;
    }

}