package store;

import model.Candidate;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CandidateStoreDBImpl extends StoreDBImpl<Candidate> {
    static Logger logger = Logger.getLogger(CandidateStoreDBImpl.class);
    private static final String FOLDER = "images" + File.separator;
    private static final CandidateStoreDBImpl INSTANCE = new CandidateStoreDBImpl();
    private final Connection connection = super.getConnection();

    public static CandidateStoreDBImpl getInstance() {
        return INSTANCE;
    }

    public void deletePhoto(Candidate candidate) {
        try {
            try {
                /* delete photo from database */
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement("UPDATE candidates SET photo = NULL WHERE id = ?")) {
                    statement.setInt(1, candidate.getId());
                    statement.executeUpdate();
                }
                try (PreparedStatement statement = connection.prepareStatement("DELETE FROM photo WHERE id = ?")) {
                    statement.setInt(1, candidate.getPhotoId());
                    statement.executeUpdate();
                }
                connection.commit();

                /* delete photo file */
                File file = new File(FOLDER + candidate.getPhotoId());
                if (file.exists()) {
                    file.delete();
                }

                /* set candidate fields */
                candidate.setPhotoId(0);
                candidate.setPhoto(null);
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        }
    }

    public void setPhoto(Candidate candidate, byte[] photo) {
        int photoId;
        try {
            if (candidate.getPhotoId() > 0) {
                deletePhoto(candidate);
            }
            try {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO photo VALUES (DEFAULT) RETURNING id")) {
                    ResultSet resultSet = statement.executeQuery();
                    resultSet.next();
                    photoId = resultSet.getInt("id");
                }
                try (PreparedStatement statement = connection.prepareStatement("UPDATE candidates SET photo = ? WHERE id = ?")) {
                    statement.setInt(1, photoId);
                    statement.setInt(2, candidate.getId());
                    statement.executeUpdate();
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } finally {
                connection.setAutoCommit(true);
            }
            File folder = new File("images");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File file = new File(folder + File.separator + photoId);
            try (FileOutputStream out = new FileOutputStream(file)) {
                out.write(photo);
            }
            candidate.setPhotoId(photoId);
            candidate.setPhoto(null);
        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        }
    }

    @Override
    public boolean add(Candidate item) {
        int rowsAffected = 0;
        int i = 1;
        try {
            if (item.getId() == 0) {
                try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO candidates (date, name, description) VALUES (?, ?, ?)")) {
                    preparedStatement.setDate(i++, new java.sql.Date(item.getDate().getTime()));
                    preparedStatement.setString(i++, item.getName());
                    preparedStatement.setString(i++, item.getDescription());
                    rowsAffected = preparedStatement.executeUpdate();
                }
            } else {
                try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE candidates SET date = ?, name = ?, description = ? WHERE id = ?")) {
                    preparedStatement.setDate(i++, new java.sql.Date(item.getDate().getTime()));
                    preparedStatement.setString(i++, item.getName());
                    preparedStatement.setString(i++, item.getDescription());
                    preparedStatement.setInt(i, item.getId());
                    rowsAffected = preparedStatement.executeUpdate();
                }
            }
            if (item.getPhoto() != null) {
                setPhoto(item, item.getPhoto());
            }
        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        }
        return rowsAffected > 0;
    }

    @Override
    public Collection<Candidate> find() {
        List<Candidate> candidates = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM candidates");
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                candidates.add(
                        new Candidate().builder()
                                .setId(resultSet.getInt("id"))
                                .setDate(resultSet.getDate("date"))
                                .setName(resultSet.getString("name"))
                                .setDescription(resultSet.getString("description"))
                                .setPhoto(resultSet.getInt("photo"))
                                .build()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return candidates;
    }

    public Collection<Candidate> find(Predicate<Candidate> candidatePredicate) {
        return find().stream().filter(candidatePredicate).collect(Collectors.toList());
    }

    public Candidate find(int id) {
        return find(candidate -> candidate.getId() == id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean delete(int id) {
        deletePhoto(find(id));
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM candidates WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}