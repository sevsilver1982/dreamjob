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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class CandidatePhotoStoreDB implements Store<Photo> {
    private static final CandidatePhotoStoreDB INSTANCE = new CandidatePhotoStoreDB();
    private static final String FOLDER = "dreamjob" + File.separator + "images" + File.separator;

    private CandidatePhotoStoreDB() {
    }

    public static CandidatePhotoStoreDB getInstance() {
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

    private Collection<Photo> executeQuery(Function<Connection, PreparedStatement> command, Predicate<Photo> predicate) {
        List<Photo> list = new ArrayList<>();
        try (Connection connection = StorePsqlC3PO.getConnection();
             PreparedStatement ps = command.apply(connection);
             ResultSet rs = ps.executeQuery()
        ) {
            if (!rs.isBeforeFirst()) {
                return list;
            }
            while (rs.next()) {
                Photo item = new Photo();
                File file = new File(FOLDER + File.separator + rs.getInt("id"));
                if (file.exists() && file.isFile()) {
                    item.setId(rs.getInt("id"));
                    item.setFile(file);
                    if (predicate == null) {
                        list.add(item);
                    } else {
                        if (predicate.test(item)) {
                            list.add(item);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private File writeFile(byte[] bytes, int id) throws IOException {
        Files.createDirectories(Paths.get(FOLDER));
        File file = new File(FOLDER + id);
        try (FileOutputStream out = new FileOutputStream(file)) {
            out.write(bytes);
        }
        return file;
    }

    public int getNextId() {
        int id = 0;
        try (Connection connection = StorePsqlC3PO.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO photo VALUES (DEFAULT) RETURNING id")
        ) {
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                return id;
            }
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public boolean add(Photo item) {
        throw new RuntimeException("Will be implemented later");
    }

    public Photo add(byte[] bytes) {
        Photo item = new Photo();
        if (bytes == null || bytes.length == 0) {
            return item;
        }
        try {
            int id = getNextId();
            item.setId(id);
            File file = writeFile(bytes, id);
            item.setFile(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return item;
    }

    @Override
    public Collection<Photo> find(Predicate<Photo> predicate) {
        return executeQuery(connection -> {
            try {
                return connection.prepareStatement("SELECT * FROM photo");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, predicate);
    }

    @Override
    public Collection<Photo> findAll() {
        return find(null);
    }

    @Override
    public Photo findById(int id) {
        Photo item = new Photo();
        try {
            Collection<Photo> list = executeQuery(connection -> {
                try {
                    PreparedStatement ps = connection.prepareStatement("SELECT * FROM photo WHERE id = ?");
                    ps.setInt(1, id);
                    return ps;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, null);
            if (!list.isEmpty()) {
                File file = new File(FOLDER + File.separator + id);
                if (file.exists() && file.isFile()) {
                    item.setId(id);
                    item.setFile(file);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return item;
    }

    @Override
    public boolean delete(int id) {
        int rowsAffected;
        try {
            rowsAffected = executeUpdate(connection -> {
                try {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM photo WHERE id = ?");
                    ps.setInt(1, id);
                    return ps;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            if (rowsAffected > 0) {
                File file = new File(FOLDER + id);
                if (file.exists()) {
                    return file.delete();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rowsAffected > 0;
    }

}