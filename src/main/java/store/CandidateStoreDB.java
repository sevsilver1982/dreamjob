package store;

import model.Candidate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CandidateStoreDB implements Store<Candidate> {
    private static final CandidateStoreDB INSTANCE = new CandidateStoreDB();

    private CandidateStoreDB() {
    }

    public static Store<Candidate> getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(Candidate item) {
        int rowsAffected = 0;
        int i = 1;
        try (Connection connection = StorePsqlC3PO.getConnection()) {
            if (item.getId() == 0) {
                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO candidates (date, name, description, photo) VALUES (?, ?, ?, ?)")) {
                    statement.setDate(i++, new java.sql.Date(item.getDate().getTime()));
                    statement.setString(i++, item.getName());
                    statement.setString(i++, item.getDescription());
                    if (item.getPhotoId() != 0) {
                        statement.setInt(i, item.getPhotoId());
                    } else {
                        statement.setNull(i, Types.INTEGER);
                    }
                    rowsAffected = statement.executeUpdate();
                }
            } else {
                try (PreparedStatement statement = connection.prepareStatement("UPDATE candidates SET date = ?, name = ?, description = ?, photo = ? WHERE id = ?")) {
                    statement.setDate(i++, new java.sql.Date(item.getDate().getTime()));
                    statement.setString(i++, item.getName());
                    statement.setString(i++, item.getDescription());
                    if (item.getPhotoId() != 0) {
                        statement.setInt(i++, item.getPhotoId());
                    } else {
                        statement.setNull(i++, Types.INTEGER);
                    }
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
    public Collection<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection connection = StorePsqlC3PO.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM candidates");
             ResultSet rs = statement.executeQuery()
        ) {
            while (rs.next()) {
                candidates.add(
                        new Candidate().builder()
                                .setId(rs.getInt("id"))
                                .setDate(rs.getDate("date"))
                                .setName(rs.getString("name"))
                                .setDescription(rs.getString("description"))
                                .setPhotoId(rs.getInt("photo"))
                                .build()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return candidates;
    }

    @Override
    public Candidate findById(int id) {
        return findAll().stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(new Candidate());
    }

    public Collection<Candidate> find(Predicate<Candidate> predicate) {
        return findAll().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = StorePsqlC3PO.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM candidates WHERE id = ?")
        ) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}