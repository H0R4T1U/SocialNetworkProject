package ubb.scs.map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ubb.scs.map.Domain.*;
import ubb.scs.map.Domain.validators.FriendshipRequestValidator;
import ubb.scs.map.Repository.database.FriendshipRequestDatabaseRepository;
import ubb.scs.map.Services.*;
import ubb.scs.map.Domain.validators.FriendshipValidator;
import ubb.scs.map.Domain.validators.UserValidator;
import ubb.scs.map.Repository.Repository;
import ubb.scs.map.Repository.database.FriendshipDatabaseRepository;
import ubb.scs.map.Repository.database.UserDatabaseRepository;
import ubb.scs.map.Services.Configs.ApplicationContext;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Repository<Long, User> userRepository = new UserDatabaseRepository(ApplicationContext.getPROPERTIES().getProperty("DB_URL"),ApplicationContext.getPROPERTIES().getProperty("DB_USERNAME"),ApplicationContext.getPROPERTIES().getProperty("DB_PASSWORD"),new UserValidator());
        Repository<Tuple<Long, Long>, Friendship> friendRepository = new FriendshipDatabaseRepository(ApplicationContext.getPROPERTIES().getProperty("DB_URL"),ApplicationContext.getPROPERTIES().getProperty("DB_USERNAME"),ApplicationContext.getPROPERTIES().getProperty("DB_PASSWORD"),new FriendshipValidator());
        Repository<Tuple<Long,Long>, FriendshipRequest> friendshipRequestRepository = new FriendshipRequestDatabaseRepository(ApplicationContext.getPROPERTIES().getProperty("DB_URL"),ApplicationContext.getPROPERTIES().getProperty("DB_USERNAME"),ApplicationContext.getPROPERTIES().getProperty("DB_PASSWORD"),new FriendshipRequestValidator());

        UserService userService = UserService.getInstance();
        userService.setRepository(userRepository);
        FriendshipService friendshipService = FriendshipService.getInstance();
        friendshipService.setRepo(friendRepository);
        FriendshipRequestService friendshipRequestService = new FriendshipRequestService(friendshipRequestRepository,friendshipService);
        NetworkService networkService = new NetworkService(friendshipService);

        UserInstance.getInstance();

        ScreenService screenService = new ScreenService(stage,userService,friendshipService,friendshipRequestService);
        // preparing loading screen
//        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("Views/login.fxml"));
//
//        // preparing main window
//        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("Views/mainWindow.fxml"));
        //screenController.addScreen("home",FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Views/hello-view.fxml"))));

        screenService.addScene("login","Views/login.fxml");
        screenService.addScene("main","Views/mainWindow.fxml");
        screenService.addScene("friendshipRequests","Views/friendRequestWindow.fxml");
        screenService.switchScene("login");


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}