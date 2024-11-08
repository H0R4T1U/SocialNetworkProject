package ubb.scs.map.Services;

import ubb.scs.map.Domain.User;
import ubb.scs.map.Repository.Repository;

import java.util.Optional;


public class UserService extends EntityService<Long, User> {
    @Override
    public Optional<User> create(User entity) {
        return super.create(entity);
    }

    public UserService(Repository<Long, User> repository) {
        super(repository);
    }


}
