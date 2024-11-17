package ubb.scs.map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ubb.scs.map.Controllers.LoginController;
import ubb.scs.map.Controllers.MainWindowController;
import ubb.scs.map.Controllers.ScreenController;
import ubb.scs.map.Domain.Friendship;
import ubb.scs.map.Domain.Tuple;
import ubb.scs.map.Domain.User;
import ubb.scs.map.Domain.validators.FriendshipValidator;
import ubb.scs.map.Domain.validators.UserValidator;
import ubb.scs.map.Repository.Repository;
import ubb.scs.map.Repository.database.FriendshipDatabaseRepository;
import ubb.scs.map.Repository.database.UserDatabaseRepository;
import ubb.scs.map.Services.Configs.ApplicationContext;
import ubb.scs.map.Services.FriendshipService;
import ubb.scs.map.Services.NetworkService;
import ubb.scs.map.Services.UserService;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Repository<Long, User> userRepository = new UserDatabaseRepository(ApplicationContext.getPROPERTIES().getProperty("DB_URL"),ApplicationContext.getPROPERTIES().getProperty("DB_USERNAME"),ApplicationContext.getPROPERTIES().getProperty("DB_PASSWORD"),new UserValidator());
        Repository<Tuple<Long, Long>, Friendship> friendRepository = new FriendshipDatabaseRepository(ApplicationContext.getPROPERTIES().getProperty("DB_URL"),ApplicationContext.getPROPERTIES().getProperty("DB_USERNAME"),ApplicationContext.getPROPERTIES().getProperty("DB_PASSWORD"),new FriendshipValidator());
        UserService userService = UserService.getInstance();
        userService.setRepository(userRepository);
        FriendshipService friendshipService = new FriendshipService(friendRepository);
        NetworkService networkService = new NetworkService(friendshipService);
        StackPane root = new StackPane();

        Scene scene = new Scene(root,800, 600);
        ScreenController screenController = new ScreenController(scene);
        // preparing loading screen
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("Views/login.fxml"));
        loginLoader.setController(new LoginController(screenController)); // Set the custom controller for login
        // preparing main window
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("Views/mainWindow.fxml"));
        mainLoader.setController(new MainWindowController(friendshipService));
        //screenController.addScreen("home",FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Views/hello-view.fxml"))));

        screenController.addScreen("login",loginLoader);
        screenController.addScreen("main",mainLoader);
        screenController.activate("login");



        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}