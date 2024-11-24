package ubb.scs.map.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import ubb.scs.map.Domain.*;
import ubb.scs.map.Utils.observer.ObservableType;
import ubb.scs.map.Utils.observer.Observer;

import java.util.Objects;
import java.util.Optional;

public class MessageController extends ControllerSuperclass implements Observer {
    @FXML
    public TextArea messageBox;
    @FXML
    private ListView<Message> messages;
    @FXML
    private ListView<Friendship> chats;


    private ObservableList<Friendship> friendships;
    private ObservableList<Message> allMessages;
    public void init() {
        chats.setCellFactory(param -> new ListCell<Friendship>() {
            @Override
            protected void updateItem(Friendship item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Long chatterId = item.getId().getE1().equals(UserInstance.getInstance().getId())
                            ? item.getId().getE2() : item.getId().getE1();
                    String chatter =  item.getUser1().equals(UserInstance.getInstance().getUsername())
                            ? item.getUser2() : item.getUser1();
                    setText(chatter);
                    setWrapText(true);
                    getStyleClass().add("list-cell");
                    setOnMouseClicked(mouseEvent -> loadMessages(chatterId));
                    setPrefWidth(param.getWidth() - 20);
                    setMinHeight(USE_COMPUTED_SIZE);
                    setMaxHeight(USE_PREF_SIZE);
                }
            }
        });
        friendships = FXCollections.observableArrayList();
        chats.setItems(friendships);
        friendships.clear();
        Optional<User> currentUser = service.getUserById(UserInstance.getInstance().getId());
        currentUser.ifPresent(user -> friendships.addAll(service.getUserFriends(user.getId())));

    }

    private void loadMessages(Long chatterId) {
        service.addObserver(this, ObservableType.MESSAGE);
        messages.setCellFactory(param -> new ListCell<Message>() {
            @Override
            protected void updateItem(Message item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    // Create a horizontal layout for sender and message text
                    HBox hBox = new HBox(10); // 10px spacing
                    hBox.setPrefWidth(param.getWidth() - 20);

                    // Label for the sender
                    String sender = service.getUserById(item.getSender()).get().getUsername();
                    Label senderLabel = new Label(sender+ ": ");
                    // Label for the actual message text
                    Label messageLabel = new Label(item.getText());
                    messageLabel.setWrapText(true);
                    senderLabel.getStyleClass().add("sender-label");
                    messageLabel.getStyleClass().add("message-label");


                    // Add labels to the HBox
                    hBox.getChildren().addAll(senderLabel, messageLabel);
                    if(!Objects.equals(sender, UserInstance.getInstance().getUsername())) {
                        hBox.setAlignment(Pos.CENTER_RIGHT);
                    }
                    // Set the HBox as the graphic of the ListCell
                    setGraphic(hBox);
                }
            }
        });
        allMessages = FXCollections.observableArrayList();
        messages.setItems(allMessages);
        allMessages.clear();
        Optional<User> currentUser = service.getUserById(UserInstance.getInstance().getId());
        currentUser.ifPresent(user -> allMessages.addAll(service.getMessages(user.getId(), chatterId)));
    }
    @FXML
    protected void sendMessage(ActionEvent actionEvent) {
        Long currentUser =UserInstance.getInstance().getId();
        Friendship x = chats.getSelectionModel().getSelectedItem();
        Long chatterId = Objects.equals(x.getId().getE1(), currentUser) ? x.getId().getE2() : x.getId().getE1();
        String message = messageBox.getText();
        service.addMessage(currentUser,chatterId,message,null);
        messageBox.clear();
    }

    @Override
    public void update() {
        reloadModel();
    }

    private void reloadModel() {
        allMessages.clear();
        Long currentUser = UserInstance.getInstance().getId();
        Friendship x = chats.getSelectionModel().getSelectedItem();
        Long chatterId = Objects.equals(x.getId().getE1(), currentUser) ? x.getId().getE2() : x.getId().getE1();
        allMessages.addAll(service.getMessages(currentUser, chatterId));
        messages.setItems(allMessages);
    }
}
