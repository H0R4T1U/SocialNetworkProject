package ubb.scs.map.Services;

import ubb.scs.map.Domain.User;
import ubb.scs.map.Repository.Repository;

import java.util.Optional;
import java.util.stream.StreamSupport;


public class UserService implements EntityService<Long, User> {

    private static UserService instance;
    private Repository<Long,User> repository;

    private UserService() {
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            // Call to the parent class's getInstance
            instance = new UserService();
        }
        return instance;
    }

    public void setRepository(Repository<Long, User> repository) {
        this.repository = repository;
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(StreamSupport.stream(getAll().spliterator(), false).filter(user -> user.getUsername().equals(username)).findFirst().orElse(null));
    }

    @Override
    public Repository<Long, User> getRepo() {
        return repository;
    }

    @Override
    public Optional<User> create(User entity) {
        return EntityService.super.create(entity);
    }

    @Override
    public Optional<User> update(User entity) {
        return EntityService.super.update(entity);
    }

    @Override
    public Optional<User> delete(Long entityId) {
        return EntityService.super.delete(entityId);
    }

    @Override
    public Iterable<User> getAll() {
        return EntityService.super.getAll();
    }

    @Override
    public Optional<User> getById(Long entityId) {
        return EntityService.super.getById(entityId);
    }
}
