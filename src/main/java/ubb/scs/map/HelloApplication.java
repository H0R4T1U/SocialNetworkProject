package ubb.scs.map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ubb.scs.map.Controllers.LoginController;
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
        UserService userService = new UserService(userRepository);
        FriendshipService friendshipService = new FriendshipService(friendRepository,userService);
        NetworkService networkService = new NetworkService(userService,friendshipService);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        LoginController loginController = fxmlLoader.getController();
        loginController.setService(userService);

        String css = Objects.requireNonNull(this.getClass().getResource("css/main.css")).toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}