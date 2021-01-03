package store;

import model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class CitiesStoreDB implements Store<City> {
    private static final Store<City> INSTANCE = new CitiesStoreDB();

    private CitiesStoreDB() {
    }

    public static Store<City> getInstance() {
        return INSTANCE;
    }

    private int executeUpdate(Function<Connection, PreparedStatement> command) {
        try (Connection connection = StorePsqlC3PO.getConnection();
             PreparedStatement ps = command.apply(connection)) {
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<City> executeQuery(Function<Connection, PreparedStatement> command, Predicate<City> predicate) {
        List<City> list = new ArrayList<>();
        try (Connection connection = StorePsqlC3PO.getConnection();
             PreparedStatement ps = command.apply(connection);
             ResultSet rs = ps.executeQuery()
        ) {
            if (!rs.isBeforeFirst()) {
                return list;
            }
            while (rs.next()) {
                City item = new City(rs.getInt("id"), rs.getString("name"));
                if (predicate == null) {
                    list.add(item);
                } else {
                    if (predicate.test(item)) {
                        list.add(item);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public boolean add(City item) {
        if (item.getId() == 0) {
            return executeUpdate(connection -> {
                try {
                    int i = 1;
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO cities (name) VALUES (?)");
                    ps.setString(i, item.getName());
                    return ps;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }) > 0;
        } else {
            return executeUpdate(connection -> {
                try {
                    int i = 1;
                    PreparedStatement ps = connection.prepareStatement("UPDATE cities SET name = ? WHERE id = ?");
                    ps.setString(i, item.getName());
                    return ps;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }) > 0;
        }
    }

    @Override
    public Collection<City> find(Predicate<City> predicate) {
        return executeQuery(connection -> {
            try {
                return connection.prepareStatement("SELECT * FROM cities");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, predicate);
    }

    @Override
    public Collection<City> findAll() {
        return find(null);
    }

    @Override
    public City findById(int id) {
        City item = new City();
        Collection<City> list = executeQuery(connection -> {
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM cities WHERE id = ?");
                ps.setInt(1, id);
                return ps;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, null);
        if (!list.isEmpty()) {
            item = list.iterator().next();
        }
        return item;
    }

    @Override
    public boolean delete(int id) {
        return executeUpdate(connection -> {
            try {
                PreparedStatement ps = connection.prepareStatement("DELETE FROM cities WHERE id = ?");
                ps.setInt(1, id);
                return ps;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }) > 0;
    }

}