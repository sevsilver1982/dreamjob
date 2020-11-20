package store;

import model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CitiesStoreDB implements Store<City> {
    private static final Store<City> INSTANCE = new CitiesStoreDB();

    private CitiesStoreDB() {
    }

    public static Store<City> getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(City item) {
        int rowsAffected;
        int i = 1;
        try (Connection connection = StorePsqlC3PO.getConnection()) {
            if (item.getId() == 0) {
                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO cities (name) VALUES (?)")) {
                    statement.setString(i, item.getName());
                    rowsAffected = statement.executeUpdate();
                }
            } else {
                try (PreparedStatement statement = connection.prepareStatement("UPDATE cities SET name = ? WHERE id = ?")) {
                    statement.setString(i++, item.getName());
                    statement.setInt(i, item.getId());
                    rowsAffected = statement.executeUpdate();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rowsAffected > 0;
    }

    @Override
    public Collection<City> findAll() {
        List<City> cities = new ArrayList<>();
        try (Connection connection = StorePsqlC3PO.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM cities");
             ResultSet rs = statement.executeQuery()
        ) {
            while (rs.next()) {
                cities.add(
                        new City(rs.getInt("id"), rs.getString("name"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cities;
    }

    @Override
    public City findById(int id) {
        return findAll().stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(new City());
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = StorePsqlC3PO.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM cities WHERE id = ?")
        ) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}