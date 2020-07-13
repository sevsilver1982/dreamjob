package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CandidateStore extends StorePLSQL<Candidate> {
    private static final CandidateStore INSTANCE = new CandidateStore();

    public static CandidateStore getInstance() {
        return INSTANCE;
    }

    @Override
    public Candidate add(Candidate item) {
        if (item.getId() == 0) {
            try (PreparedStatement preparedStatement = super.getConnection().prepareStatement("INSERT INTO candidates (date, name, description) VALUES (?, ?, ?)")
            ) {
                int i = 1;
                preparedStatement.setDate(i++, new java.sql.Date(item.getDate().getTime()));
                preparedStatement.setString(i++, item.getName());
                preparedStatement.setString(i++, item.getDescription());
                int rowsAffected = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (PreparedStatement preparedStatement = super.getConnection().prepareStatement("UPDATE candidates SET date = ?, name = ?, description = ? WHERE id = ?")
            ) {
                int i = 1;
                preparedStatement.setDate(i++, new java.sql.Date(item.getDate().getTime()));
                preparedStatement.setString(i++, item.getName());
                preparedStatement.setString(i++, item.getDescription());
                preparedStatement.setInt(i, item.getId());
                int rowsAffected = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return item;
    }

    @Override
    public Collection<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement("SELECT * FROM candidates ORDER BY date DESC");
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                candidates.add(new Candidate().builder()
                        .setId(resultSet.getInt("id"))
                        .setDate(resultSet.getDate("date"))
                        .setName(resultSet.getString("name"))
                        .setDescription(resultSet.getString("description"))
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
        Candidate candidate = new Candidate();
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement("SELECT * FROM candidates WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                candidate = new Candidate().builder()
                        .setId(resultSet.getInt("id"))
                        .setDate(resultSet.getDate("date"))
                        .setName(resultSet.getString("name"))
                        .setDescription(resultSet.getString("description"))
                        .build();
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return candidate;
    }

}