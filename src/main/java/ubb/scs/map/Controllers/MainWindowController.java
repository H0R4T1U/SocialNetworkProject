package ubb.scs.map.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ubb.scs.map.Domain.Friendship;
import ubb.scs.map.Domain.UserInstance;
import ubb.scs.map.Services.FriendshipService;
import ubb.scs.map.Utils.events.FriendshipEntityChangeEvent;
import ubb.scs.map.Utils.observer.Observer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainWindowController implements Observer<FriendshipEntityChangeEvent> {
    FriendshipService service;
    ObservableList<Friendship> model = FXCollections.observableArrayList();

    public MainWindowController(FriendshipService service) {
        this.service = service;
    }

    @FXML
    TableView<Friendship> tableView;
    @FXML
    TableColumn<Friendship, String> tableColumnName;

    @FXML
    public void initialize() {
        service.addObserver(this);
        initModel();
        tableColumnName.setCellValueFactory(cellData -> {
            Friendship f = cellData.getValue();
            if(f.getId().getE1().equals(UserInstance.getInstance().getId())){
                return new SimpleStringProperty(f.getUser2());
            }else{
                return new SimpleStringProperty(f.getUser1());
            }
        });
        tableView.setItems(model);
    }

    private void initModel() {
        List<Friendship> friends = service.getUserFriends(UserInstance.getInstance().getId());
        model.setAll(friends);
    }

    @Override
    public void update(FriendshipEntityChangeEvent friendshipEntityChangeEvent) {
        initModel();
    }


}
