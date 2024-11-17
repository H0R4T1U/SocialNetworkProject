package ubb.scs.map.Repository.database;


import ubb.scs.map.Domain.Friendship;
import ubb.scs.map.Domain.Tuple;
import ubb.scs.map.Domain.validators.Validator;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// TODO adauga noi parametri + baza de date
// TODO FriendRequest
// TODO Add/Remove friend buttons
// TODO fa o sa arate frumos
public class FriendshipDatabaseRepository extends DatabaseRepository<Tuple<Long, Long>, Friendship> {

    public FriendshipDatabaseRepository(String url, String username, String password, Validator<Friendship> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Optional<Friendship> findOne(Tuple<Long, Long> longLongTuple) {
        String FIND_ONE_QUERY = "SELECT * FROM \"Friendship\" WHERE \"ID1\" = ? AND \"ID2\" = ?";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ONE_QUERY)) {
            preparedStatement.setLong(1, longLongTuple.getE1());
            preparedStatement.setLong(2, longLongTuple.getE2());
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapToFriendship(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Friendship> findAll() {
        List<Friendship> friendships = new ArrayList<>();
        String FIND_ALL_QUERY = "SELECT * FROM \"Friendship\"";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                friendships.add(mapToFriendship(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return friendships;
    }

    @Override
    public Optional<Friendship> save(Friendship friendship) {
        validator.validate(friendship);

        String SAVE_QUERY = "INSERT INTO \"Friendship\" (\"ID1\",\"ID2\",\"Date\",\"User1\",\"User2\") VALUES(?,?,?,?,?)";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_QUERY)) {
            preparedStatement.setLong(1, friendship.getId().getE1());
            preparedStatement.setLong(2, friendship.getId().getE2());
            preparedStatement.setDate(3, java.sql.Date.valueOf(friendship.getDate()));
            preparedStatement.setString(4, friendship.getUser1());
            preparedStatement.setString(5, friendship.getUser2());
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

        String DELETE_QUERY = "DELETE FROM \"Friendship\" WHERE \"ID1\" = ? AND \"ID2\" = ?";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
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

    private Friendship mapToFriendship(ResultSet resultSet) throws SQLException {
        Tuple<Long, Long> id = new Tuple<>(resultSet.getLong("ID1"), resultSet.getLong("ID2"));
        Friendship friendship = new Friendship();
        friendship.setId(id);
        friendship.setDate(resultSet.getDate("Date").toLocalDate());
        friendship.setUser1(resultSet.getString("User1"));
        friendship.setUser2(resultSet.getString("User2"));

        return friendship;
    }
}