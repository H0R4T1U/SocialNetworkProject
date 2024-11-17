package ubb.scs.map.Repository.database;

import ubb.scs.map.Domain.User;
import ubb.scs.map.Domain.validators.Validator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDatabaseRepository extends DatabaseRepository<Long, User> {

    public UserDatabaseRepository(String url, String username, String password, Validator<User> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Optional<User> findOne(Long id) {
        String FIND_ONE_QUERY = "select * from \"User\" where id=?";
        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ONE_QUERY)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return Optional.of(createUser(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<User> findAll() {
        List<User> users = new ArrayList<>();
        String FIND_ALL_QUERY = "select * from \"User\"";
        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next())
                users.add(createUser(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public Optional<User> save(User user) {
        validator.validate(user);
        String SAVE_QUERY = "insert into \"User\" (\"id\",\"username\",\"password\",\"phone\",\"joindate\",\"age\" )  values(?,?,?,?,?,?)";
        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement(SAVE_QUERY)) {
            ps.setLong(1, user.getId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getJoinDate().toString());
            ps.setInt(6, user.getAge());
            ps.execute();
            return Optional.of(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> delete(Long id) {
        Optional<User> user = findOne(id);
        String DELETE_QUERY = "delete from \"User\" where id=?";
        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {
            ps.setLong(1, id);
            ps.execute();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> update(User entity) {
        return Optional.empty();
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(getUrl(), getUsername(), getPassword());
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user =  new User(resultSet.getString(2), resultSet.getString(3),resultSet.getString(4), LocalDateTime.parse(resultSet.getString(6)),resultSet.getInt(5));
        user.setId(resultSet.getLong(1));
        return user;
    }
}