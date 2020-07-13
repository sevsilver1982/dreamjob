package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OfferStore extends StorePLSQL<Offer> {
    private static final OfferStore INSTANCE = new OfferStore();

    public static OfferStore getInstance() {
        return INSTANCE;
    }

    @Override
    public Offer add(Offer item) {
        if (item.getId() == 0) {
            try (PreparedStatement preparedStatement = super.getConnection().prepareStatement("INSERT INTO offers (date, name, author, text) VALUES (?, ?, ?, ?)")
            ) {
                int i = 1;
                preparedStatement.setDate(i++, new java.sql.Date(item.getDate().getTime()));
                preparedStatement.setString(i++, item.getName());
                preparedStatement.setString(i++, item.getAuthor());
                preparedStatement.setString(i, item.getText());
                int rowsAffected = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (PreparedStatement preparedStatement = super.getConnection().prepareStatement("UPDATE offers SET date = ?, name = ?, author = ?, text = ? WHERE id = ?")
            ) {
                int i = 1;
                preparedStatement.setDate(i++, new java.sql.Date(item.getDate().getTime()));
                preparedStatement.setString(i++, item.getName());
                preparedStatement.setString(i++, item.getAuthor());
                preparedStatement.setString(i++, item.getText());
                preparedStatement.setInt(i, item.getId());
                int rowsAffected = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return item;
    }

    @Override
    public Collection<Offer> findAll() {
        List<Offer> offers = new ArrayList<>();
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement("SELECT * FROM offers ORDER BY date DESC");
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                offers.add(new Offer().builder()
                        .setId(resultSet.getInt("id"))
                        .setDate(resultSet.getDate("date"))
                        .setName(resultSet.getString("name"))
                        .setAuthor(resultSet.getString("author"))
                        .setText(resultSet.getString("text"))
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
        Offer offer = new Offer();
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement("SELECT * FROM offers WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                offer = new Offer().builder()
                        .setId(resultSet.getInt("id"))
                        .setDate(resultSet.getDate("date"))
                        .setName(resultSet.getString("name"))
                        .setAuthor(resultSet.getString("author"))
                        .setText(resultSet.getString("text"))
                        .build();
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return offer;
    }

}