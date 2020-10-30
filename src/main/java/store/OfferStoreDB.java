package store;

import model.Offer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OfferStoreDB implements Store<Offer> {
    private static final OfferStoreDB INSTANCE = new OfferStoreDB();

    private OfferStoreDB() {
    }

    public static Store<Offer> getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(Offer item) {
        int rowsAffected = 0;
        int i = 1;
        try (Connection connection = StorePsqlC3PO.getConnection()) {
            if (item.getId() == 0) {
                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO offers (date, name, author, text) VALUES (?, ?, ?, ?) RETURNING id")) {
                    statement.setDate(i++, new java.sql.Date(item.getDate().getTime()));
                    statement.setString(i++, item.getName());
                    statement.setString(i++, item.getAuthor());
                    statement.setString(i, item.getText());
                    rowsAffected = statement.executeUpdate();
                }
            } else {
                try (PreparedStatement statement = connection.prepareStatement("UPDATE offers SET date = ?, name = ?, author = ?, text = ? WHERE id = ?")) {
                    statement.setDate(i++, new java.sql.Date(item.getDate().getTime()));
                    statement.setString(i++, item.getName());
                    statement.setString(i++, item.getAuthor());
                    statement.setString(i++, item.getText());
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
    public Collection<Offer> findAll() {
        List<Offer> offers = new ArrayList<>();
        try (Connection connection = StorePsqlC3PO.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM offers");
             ResultSet rs = statement.executeQuery()
        ) {
            while (rs.next()) {
                offers.add(
                        new Offer().builder()
                                .setId(rs.getInt("id"))
                                .setDate(rs.getDate("date"))
                                .setName(rs.getString("name"))
                                .setAuthor(rs.getString("author"))
                                .setText(rs.getString("text"))
                                .build()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return offers;
    }

    @Override
    public Offer findById(int id) {
        return findAll().stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(new Offer());
    }

    public Collection<Offer> find(Predicate<Offer> predicate) {
        return findAll().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = StorePsqlC3PO.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM offers WHERE id = ?")
        ) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}