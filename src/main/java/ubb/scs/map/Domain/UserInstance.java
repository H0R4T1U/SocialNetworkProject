package ubb.scs.map.Domain;

public class UserInstance {
    private static UserInstance instance;
    private final String username;

    public UserInstance(String username) {
        this.username = username;
    }

    public static UserInstance getInstance(String username) {
        if (instance == null) {
            instance = new UserInstance(username);
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }
}
