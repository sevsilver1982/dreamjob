package store;

import model.Offer;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OfferStoreDBImpl extends StoreDBImpl<Offer> {
    static Logger logger = Logger.getLogger(OfferStoreDBImpl.class);
    private static final OfferStoreDBImpl INSTANCE = new OfferStoreDBImpl();
    private final Connection connection = super.getConnection();

    public static OfferStoreDBImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(Offer item) {
        int rowsAffected = 0;
        int i = 1;
        try {
            if (item.getId() == 0) {
                try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO offers (date, name, author, text) VALUES (?, ?, ?, ?)")) {
                    preparedStatement.setDate(i++, new java.sql.Date(item.getDate().getTime()));
                    preparedStatement.setString(i++, item.getName());
                    preparedStatement.setString(i++, item.getAuthor());
                    preparedStatement.setString(i, item.getText());
                    rowsAffected = preparedStatement.executeUpdate();
                }
            } else {
                try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE offers SET date = ?, name = ?, author = ?, text = ? WHERE id = ?")) {
                    preparedStatement.setDate(i++, new java.sql.Date(item.getDate().getTime()));
                    preparedStatement.setString(i++, item.getName());
                    preparedStatement.setString(i++, item.getAuthor());
                    preparedStatement.setString(i++, item.getText());
                    preparedStatement.setInt(i, item.getId());
                    rowsAffected = preparedStatement.executeUpdate();
                }
            }
        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        }
        return rowsAffected > 0;
    }

    @Override
    public Collection<Offer> find() {
        List<Offer> offers = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM offers");
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                offers.add(
                        new Offer().builder()
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

    public Collection<Offer> find(Predicate<Offer> offerPredicate) {
        return find().stream().filter(offerPredicate).collect(Collectors.toList());
    }

    public Offer find(int id) {
        return find(offer -> offer.getId() == id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean delete(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM offers WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}