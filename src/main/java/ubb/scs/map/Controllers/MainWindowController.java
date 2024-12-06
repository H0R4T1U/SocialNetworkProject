package ubb.scs.map.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ubb.scs.map.Domain.*;
import ubb.scs.map.Utils.observer.ObservableType;
import ubb.scs.map.Utils.observer.Observer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class MainWindowController extends ControllerSuperclass implements Observer{
    private final ObservableList<Friendship> model = FXCollections.observableArrayList();
    private ObservableList<User> userModel = FXCollections.observableArrayList();

    @FXML
    private ListView<User> userList;

    @FXML
    private TextField searchBar;
    @FXML
    private TableView<Friendship> tableView;
    @FXML
    private TableColumn<Friendship, String> tableColumnName;

    public void init() {
        service.addObserver(this, ObservableType.FRIENDSHIP);
        service.addObserver(this, ObservableType.USER);

        initModel();
        Optional<User> user = service.getUserById(UserInstance.getInstance().getId());
        // Alerta Friend Request
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

        userList.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setText(item.toString());
                    setWrapText(true);
                    setPrefWidth(param.getWidth() - 20);
                    setMinHeight(USE_COMPUTED_SIZE);
                    setMaxHeight(USE_PREF_SIZE);
                }
            }
        });
        userModel = FXCollections.observableArrayList();
        userList.setItems(userModel);
        userModel.clear();
        service.getAllUsers().forEach(usr -> {
            userModel.add(usr);
        });
        userList.setItems(userModel);
    }

    private void initModel() {
        List<Friendship> friends = service.getUserFriends(UserInstance.getInstance().getId());
        List<User> users = (List<User>) service.getAllUsers();
        model.setAll(friends);
        userModel.setAll(users);


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
