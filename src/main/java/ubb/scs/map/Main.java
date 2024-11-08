package ubb.scs.map;

import ubb.scs.map.Domain.Friendship;
import ubb.scs.map.Domain.Tuple;
import ubb.scs.map.Domain.User;
import ubb.scs.map.Domain.validators.FriendshipValidator;
import ubb.scs.map.Domain.validators.UserValidator;
import ubb.scs.map.Repository.Repository;
import ubb.scs.map.Repository.database.FriendshipDatabaseRepository;
import ubb.scs.map.Repository.database.UserDatabaseRepository;
import ubb.scs.map.Services.FriendshipService;
import ubb.scs.map.Services.NetworkService;
import ubb.scs.map.Services.UserService;
import ubb.scs.map.Ui.Console;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);

            String url = properties.getProperty("DB_URL");
            String username = properties.getProperty("DB_USERNAME");
            String password = properties.getProperty("DB_PASSWORD");

            Repository<Long, User> userRepository = new UserDatabaseRepository(url,username,password,new UserValidator());
            Repository<Tuple<Long, Long>, Friendship> friendRepository = new FriendshipDatabaseRepository(url,username,password,new FriendshipValidator());
            UserService userService = new UserService(userRepository);
            FriendshipService friendshipService = new FriendshipService(friendRepository,userService);
            NetworkService networkService = new NetworkService(userService,friendshipService);
            Console console = new Console(userService,friendshipService,networkService);
            console.MainMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}