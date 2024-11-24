package ubb.scs.map.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController extends ControllerSuperclass {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;





    @FXML
    protected void login() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        usernameField.clear();
        passwordField.clear();
        if (service.login(username,password)) {
            System.out.println(username + " Logged in!");
            service.switchScene("main");
        } else{
            System.out.println("Invalid username or password!");
        }

    }
    @FXML
    protected void register() throws IOException {
        service.switchScene("register");
    }
}
