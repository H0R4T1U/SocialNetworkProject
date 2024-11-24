package ubb.scs.map.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import ubb.scs.map.Domain.Friendship;
import ubb.scs.map.Domain.FriendshipRequest;
import ubb.scs.map.Domain.User;
import ubb.scs.map.Domain.UserInstance;
import ubb.scs.map.Services.FriendshipRequestService;
import ubb.scs.map.Services.ScreenService;
import ubb.scs.map.Services.UserService;
import ubb.scs.map.Utils.observer.ObservableType;
import ubb.scs.map.Utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.Optional;

public class FriendRequestsController extends ControllerSuperclass implements Observer {
    @FXML
    private ListView<FriendshipRequest> listViewRequests;


    private ObservableList<FriendshipRequest> requestList;
    public void init() {
        service.addObserver(this, ObservableType.REQUEST);
        listViewRequests.setCellFactory(param -> new ListCell<FriendshipRequest>() {
            @Override
            protected void updateItem(FriendshipRequest item, boolean empty) {
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
        requestList = FXCollections.observableArrayList();
        listViewRequests.setItems(requestList);
        requestList.clear();
        Optional<User> currentUser = service.getUserById(UserInstance.getInstance().getId());
        currentUser.ifPresent(user -> service.getRequestsForUser(user)
                .forEach(r -> requestList.add(r)));

    }
    private void reloadModel(){
        requestList.clear();
        Optional<User> currentUser = service.getUserById(UserInstance.getInstance().getId());
        currentUser.ifPresent(user -> service.getRequestsForUser(user)
                .forEach(r -> requestList.add(r)));
        listViewRequests.setItems(requestList);
    }
    @FXML
    protected void handleButtonAcceptRequestClicked(ActionEvent actionEvent) {
        var selectedItems = listViewRequests.getSelectionModel().getSelectedItems();
        FriendshipRequest friendshipRequest = (FriendshipRequest) selectedItems.getFirst();
        String user1 = service.getUserById(friendshipRequest.getSender()).get().getUsername();
        String user2 = service.getUserById(friendshipRequest.getReceiver()).get().getUsername();
        Friendship fr = new Friendship(LocalDateTime.now(),user1,user2);
        fr.setId(friendshipRequest.getId());
        service.createFriendship(fr);
        requestList.remove(friendshipRequest);
        service.removeFriendshipRequest(friendshipRequest);

    }
    @FXML
    protected void handleButtonDeleteRequestClicked(ActionEvent actionEvent) {
        var selectedItems = listViewRequests.getSelectionModel().getSelectedItems();
        FriendshipRequest friendshipRequest = (FriendshipRequest) selectedItems.getFirst();
        requestList.remove(friendshipRequest);
        service.removeFriendshipRequest(friendshipRequest);
    }
    @FXML
    protected void backToMain(ActionEvent actionEvent) {
        service.switchScene("main");
    }

    @Override
    public void update() {
        reloadModel();
    }
}
