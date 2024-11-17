package ubb.scs.map.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ubb.scs.map.Domain.User;
import ubb.scs.map.Domain.UserInstance;
import ubb.scs.map.Services.UserService;

import java.io.IOException;
import java.util.Optional;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    private final UserService service = UserService.getInstance();
    private final ScreenController screenController;

    public LoginController(ScreenController screenController) {
        this.screenController = screenController;
    }


    @FXML
    protected void Login() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Optional<User> user = service.findByUsername(username);
        if (user.isPresent()) {
            if(user.get().getPassword().equals(password)) {
                UserInstance.getInstance(user.get().getId());
                System.out.println(user.get().getUsername() + " Logged in!");
                screenController.activate("main");
            }
        }else{
            System.out.println("Invalid username or password!");
        }

    }
}
