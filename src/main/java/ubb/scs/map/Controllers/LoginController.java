package ubb.scs.map.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ubb.scs.map.Domain.User;
import ubb.scs.map.Domain.UserInstance;
import ubb.scs.map.Services.UserService;

import java.util.Optional;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    UserService service;

    public void setService(UserService service) {
        this.service = service;
    }

    @FXML
    protected void Login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Optional<User> user = service.findByUsername(username);
        if (user.isPresent()) {
            if(user.get().getPassword().equals(password)) {
                UserInstance.getInstance(username);
                System.out.println(user.get().getUsername() + " Logged in!");
            }
        }else{
            System.out.println("Invalid username or password!");
        }

    }
}
