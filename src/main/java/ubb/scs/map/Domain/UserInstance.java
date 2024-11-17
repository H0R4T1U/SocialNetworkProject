package ubb.scs.map.Domain;

public class UserInstance {
    private static UserInstance instance;
    private final Long id;

    public UserInstance(Long id) {
        this.id = id;
    }

    public static UserInstance getInstance(Long id) {
        if (instance == null) {
            instance = new UserInstance(id);
        }
        return instance;
    }
    public static UserInstance getInstance() {
        return instance;
    }

    public Long getId() {
        return id;
    }
}
