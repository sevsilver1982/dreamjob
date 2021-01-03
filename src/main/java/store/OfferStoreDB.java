package store;

import model.Offer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class OfferStoreDB implements Store<Offer> {
    private static final Store<Offer> INSTANCE = new OfferStoreDB();

    private OfferStoreDB() {
    }

    public static Store<Offer> getInstance() {
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

    private Collection<Offer> executeQuery(Function<Connection, PreparedStatement> command, Predicate<Offer> predicate) {
        List<Offer> list = new ArrayList<>();
        try (Connection connection = StorePsqlC3PO.getConnection();
             PreparedStatement ps = command.apply(connection);
             ResultSet rs = ps.executeQuery()
        ) {
            if (!rs.isBeforeFirst()) {
                return list;
            }
            while (rs.next()) {
                Offer item = new Offer().builder()
                        .setId(rs.getInt("id"))
                        .setDate(rs.getDate("date"))
                        .setName(rs.getString("name"))
                        .setAuthor(rs.getString("author"))
                        .setText(rs.getString("text"))
                        .build();
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
    public boolean add(Offer item) {
        if (item.getId() == 0) {
            return executeUpdate(connection -> {
                try {
                    int i = 1;
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO offers (date, name, author, text) VALUES (?, ?, ?, ?) RETURNING id");
                    ps.setDate(i++, new java.sql.Date(item.getDate().getTime()));
                    ps.setString(i++, item.getName());
                    ps.setString(i++, item.getAuthor());
                    ps.setString(i, item.getText());
                    return ps;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }) > 0;
        } else {
            return executeUpdate(connection -> {
                try {
                    int i = 1;
                    PreparedStatement ps = connection.prepareStatement("UPDATE offers SET date = ?, name = ?, author = ?, text = ? WHERE id = ?");
                    ps.setDate(i++, new java.sql.Date(item.getDate().getTime()));
                    ps.setString(i++, item.getName());
                    ps.setString(i++, item.getAuthor());
                    ps.setString(i++, item.getText());
                    ps.setInt(i, item.getId());
                    return ps;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }) > 0;
        }
    }

    @Override
    public Collection<Offer> find(Predicate<Offer> predicate) {
        return executeQuery(connection -> {
            try {
                return connection.prepareStatement("SELECT * FROM offers");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, predicate);
    }

    @Override
    public Collection<Offer> findAll() {
        return find(null);
    }

    @Override
    public Offer findById(int id) {
        Offer item = new Offer();
        Collection<Offer> list = executeQuery(connection -> {
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM offers WHERE id = ?");
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
                PreparedStatement ps = connection.prepareStatement("DELETE FROM offers WHERE id = ?");
                ps.setInt(1, id);
                return ps;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }) > 0;
    }

}