package store;

import model.Photo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CandidatePhotoStoreDB {
    private static final CandidatePhotoStoreDB INSTANCE = new CandidatePhotoStoreDB();
    private static final String FOLDER = "dreamjob" + File.separator + "images" + File.separator;

    private CandidatePhotoStoreDB() {
    }

    public static CandidatePhotoStoreDB getInstance() {
        return INSTANCE;
    }

    private File writeFile(byte[] bytes, int id) throws IOException {
        Files.createDirectories(Paths.get(FOLDER));
        File file = new File(FOLDER + id);
        try (FileOutputStream out = new FileOutputStream(file)) {
            out.write(bytes);
        }
        return file;
    }

    public Photo add(byte[] bytes) {
        Photo photo = new Photo();
        if (bytes == null || bytes.length == 0) {
            return photo;
        }
        try (Connection connection = StorePsqlC3PO.getConnection()) {
            int id;
            try {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO photo VALUES (DEFAULT) RETURNING id")) {
                    ResultSet rs = statement.executeQuery();
                    rs.next();
                    id = rs.getInt("id");
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } finally {
                connection.setAutoCommit(true);
            }
            photo.setId(id);
            photo.setFile(writeFile(bytes, id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return photo;
    }

    public Collection<Photo> findAll() {
        List<Photo> offers = new ArrayList<>();
        try (Connection connection = StorePsqlC3PO.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM offers");
             ResultSet rs = statement.executeQuery()
        ) {
            while (rs.next()) {
                Photo photo = new Photo();
                File file = new File(FOLDER + File.separator + rs.getInt("id"));
                if (file.exists() && file.isFile()) {
                    photo.setId(rs.getInt("id"));
                    photo.setFile(file);
                    offers.add(photo);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return offers;
    }

    public Photo findById(int id) {
        return findAll().stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(new Photo());
    }

    public boolean delete(int id) {
        try (Connection connection = StorePsqlC3PO.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM photo WHERE id = ?")
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
            File file = new File(FOLDER + id);
            if (file.exists()) {
                file.delete();
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}