package store;

import model.Candidate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class CandidateStoreDB implements Store<Candidate> {
    private static final Store<Candidate> INSTANCE = new CandidateStoreDB();

    private CandidateStoreDB() {
    }

    public static Store<Candidate> getInstance() {
        return INSTANCE;
    }

    public int executeUpdate(Function<Connection, PreparedStatement> command) {
        try (Connection connection = StorePsqlC3PO.getConnection();
             PreparedStatement ps = command.apply(connection)) {
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<Candidate> executeQuery(Function<Connection, PreparedStatement> command, Predicate<Candidate> predicate) {
        List<Candidate> list = new ArrayList<>();
        try (Connection connection = StorePsqlC3PO.getConnection();
             PreparedStatement ps = command.apply(connection);
             ResultSet rs = ps.executeQuery()
        ) {
            if (!rs.isBeforeFirst()) {
                return list;
            }
            while (rs.next()) {
                Candidate item = new Candidate().builder()
                        .setId(rs.getInt("id"))
                        .setDate(rs.getDate("date"))
                        .setName(rs.getString("name"))
                        .setDescription(rs.getString("description"))
                        .setPhotoId(rs.getInt("photo"))
                        .setCity(CitiesStoreDB.getInstance().findById(rs.getInt("city")))
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
    public boolean add(Candidate item) {
        if (item.getId() == 0) {
            return executeUpdate(connection -> {
                try {
                    int i = 1;
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO candidates (date, name, description, photo, city) VALUES (?, ?, ?, ?, ?)");
                    ps.setDate(i++, new java.sql.Date(item.getDate().getTime()));
                    ps.setString(i++, item.getName());
                    ps.setString(i++, item.getDescription());
                    if (item.getPhotoId() != 0) {
                        ps.setInt(i++, item.getPhotoId());
                    } else {
                        ps.setNull(i++, Types.INTEGER);
                    }
                    if (item.getCity().getId() != 0) {
                        ps.setInt(i, item.getCity().getId());
                    } else {
                        ps.setNull(i, Types.INTEGER);
                    }
                    return ps;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }) > 0;
        } else {
            return executeUpdate(connection -> {
                try {
                    int i = 1;
                    PreparedStatement ps = connection.prepareStatement("UPDATE candidates SET date = ?, name = ?, description = ?, photo = ?, city = ? WHERE id = ?");
                    ps.setDate(i++, new java.sql.Date(item.getDate().getTime()));
                    ps.setString(i++, item.getName());
                    ps.setString(i++, item.getDescription());
                    if (item.getPhotoId() != 0) {
                        ps.setInt(i++, item.getPhotoId());
                    } else {
                        ps.setNull(i++, Types.INTEGER);
                    }
                    if (item.getCity().getId() != 0) {
                        ps.setInt(i++, item.getCity().getId());
                    } else {
                        ps.setNull(i++, Types.INTEGER);
                    }
                    ps.setInt(i, item.getId());
                    return ps;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }) > 0;
        }
    }

    @Override
    public Collection<Candidate> find(Predicate<Candidate> predicate) {
        return executeQuery(connection -> {
            try {
                return connection.prepareStatement("SELECT * FROM candidates");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, predicate);
    }

    @Override
    public Collection<Candidate> findAll() {
        return find(null);
    }

    @Override
    public Candidate findById(int id) {
        Candidate item = new Candidate();
        Collection<Candidate> list = executeQuery(connection -> {
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM candidates WHERE id = ?");
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
                PreparedStatement ps = connection.prepareStatement("DELETE FROM candidates WHERE id = ?");
                ps.setInt(1, id);
                return ps;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }) > 0;
    }

}