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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Main {
    public static void main(String[] args) {
        HelloApplication.main(args);

    }
}