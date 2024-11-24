package ubb.scs.map.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ubb.scs.map.Domain.*;
import ubb.scs.map.Utils.observer.ObservableType;
import ubb.scs.map.Utils.observer.Observer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class MainWindowController extends ControllerSuperclass implements Observer{
    private final ObservableList<Friendship> model = FXCollections.observableArrayList();

    @FXML
    private TextField searchBar;
    @FXML
    private TableView<Friendship> tableView;
    @FXML
    private TableColumn<Friendship, String> tableColumnName;

    public void init() {
        service.addObserver(this, ObservableType.FRIENDSHIP);
        initModel();
        Optional<User> user = service.getUserById(UserInstance.getInstance().getId());
        if(user.isPresent()){
            Iterable<FriendshipRequest> requests = service.getRequestsForUser(user.get());
            boolean hasRequests = requests.iterator().hasNext();
            if (hasRequests) {
                new Thread(() -> {
                    MessageAlert.showInfo("You have pending friendship requests!");
                }).start();
            }
        }
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
    public void update() {
        initModel();
    }

    @FXML
    protected void logout() throws IOException {
        UserInstance.getInstance().setId((long)-1);
        service.switchScene("login");
    }
    @FXML
    protected void removeFriend() {
        Friendship friendship=(Friendship) tableView.getSelectionModel().getSelectedItem();
        service.deleteFriendship(friendship.getId());

    }
    @FXML
    protected void addFriend() throws IOException {
        String user = searchBar.getText();
        searchBar.clear();
        Optional<User> snd = service.getUserById(UserInstance.getInstance().getId());
        Optional<User> rcv = service.getUserByName(user);
        try {
            if (rcv.isPresent() && snd.isPresent()) {
                service.createFriendshipRequest(snd.get(), rcv.get());
            }
        }catch (Exception e){
            MessageAlert.showWarning(e.getMessage());
        }
    }
    @FXML
    protected void toRequests() throws IOException {
        service.switchScene("friendshipRequests");
    }
    @FXML
    protected void toMessages() throws IOException {
        service.switchScene("messages");
    }
}
