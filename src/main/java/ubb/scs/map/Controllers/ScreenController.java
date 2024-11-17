package ubb.scs.map.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;

public class ScreenController {
    private final HashMap<String, FXMLLoader> screenMap = new HashMap<>();
    private final Scene main;

    public ScreenController(Scene main) {
        this.main = main;
    }

    public void addScreen(String name, FXMLLoader loader){
        screenMap.put(name, loader);
    }

    protected void removeScreen(String name){
        screenMap.remove(name);
    }

    public void activate(String name) throws IOException {
        main.setRoot( screenMap.get(name).load() );
    }
}