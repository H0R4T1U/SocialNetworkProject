package ubb.scs.map.Repository.database;


import ubb.scs.map.Domain.Friendship;
import ubb.scs.map.Domain.Tuple;
import ubb.scs.map.Domain.validators.Validator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FriendshipDatabaseRepository extends DatabaseRepository<Tuple<Long, Long>, Friendship> {

    public FriendshipDatabaseRepository(String url, String username, String password, Validator<Friendship> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Optional<Friendship> findOne(Tuple<Long, Long> longLongTuple) {
        String sql = "SELECT * FROM \"Friendship\" WHERE \"ID1\" = ? AND \"ID2\" = ?";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, longLongTuple.getE1());
            preparedStatement.setLong(2, longLongTuple.getE2());
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapToFriendship(longLongTuple));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Friendship> findAll() {
        List<Friendship> friendships = new ArrayList<>();
        String sql = "SELECT * FROM \"Friendship\"";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Tuple<Long, Long> id = new Tuple<>(resultSet.getLong("ID1"), resultSet.getLong("ID2"));
                friendships.add(mapToFriendship(id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return friendships;
    }

    @Override
    public Optional<Friendship> save(Friendship friendship) {
        validator.validate(friendship);
        String sql = "INSERT INTO \"Friendship\" (\"ID1\",\"ID2\") VALUES(?,?)";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, friendship.getId().getE1());
            preparedStatement.setLong(2, friendship.getId().getE2());
            preparedStatement.execute();
            return Optional.of(friendship);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> delete(Tuple<Long, Long> longLongTuple) {
        Optional<Friendship> friendship = findOne(longLongTuple);
        if (friendship.isEmpty())
            return Optional.empty();
        String sql = "DELETE FROM \"Friendship\" WHERE \"ID1\" = ? AND \"ID2\" = ?";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, longLongTuple.getE1());
            preparedStatement.setLong(2, longLongTuple.getE2());
            preparedStatement.executeUpdate();
            return friendship;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> update(Friendship entity) {
        return Optional.empty();
    }

    private Connection prepareConnection() throws SQLException {
        return DriverManager.getConnection(getUrl(), getUsername(), getPassword());
    }

    private Friendship mapToFriendship(Tuple<Long, Long> id) {
        Friendship friendship = new Friendship();
        friendship.setId(id);
        return friendship;
    }
}