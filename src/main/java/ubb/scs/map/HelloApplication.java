package ubb.scs.map;

import javafx.application.Application;
import javafx.stage.Stage;
import ubb.scs.map.Domain.*;
import ubb.scs.map.Domain.validators.FriendshipRequestValidator;
import ubb.scs.map.Facades.RepositoryFacade;
import ubb.scs.map.Facades.ServiceFacade;
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
        RepositoryFacade.getInstance();
        UserInstance.getInstance();
        ServiceFacade.getInstance();

        ScreenService screenService = new ScreenService(stage);
        ServiceFacade.getInstance().setScreenService(screenService);
        // preparing loading screen
//        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("Views/login.fxml"));
//
//        // preparing main window
//        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("Views/mainWindow.fxml"));
        //screenController.addScreen("home",FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Views/hello-view.fxml"))));

        screenService.addScene("login","Views/login.fxml");
        screenService.addScene("main","Views/mainWindow.fxml");
        screenService.addScene("friendshipRequests","Views/friendRequestWindow.fxml");
        screenService.addScene("messages","Views/Messages.fxml");
        screenService.switchScene("login");


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}