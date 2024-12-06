package ubb.scs.map.Facades;

import ubb.scs.map.Domain.*;
import ubb.scs.map.Services.*;
import ubb.scs.map.Utils.observer.ObservableType;
import ubb.scs.map.Utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ServiceFacade {
    private final UserService userService;
    private final FriendshipService friendshipService;
    private final FriendshipRequestService friendshipRequestService;
    private ScreenService screenService = null    ;
    private final NetworkService networkService;
    private final MessageService messageService;

    private static ServiceFacade instance = null;
    private ServiceFacade() {
        RepositoryFacade rf = RepositoryFacade.getInstance();
        this.userService = new UserService(rf.getUserRepository());
        this.friendshipService = new FriendshipService(rf.getFriendshipRepository());
        this.friendshipRequestService = new FriendshipRequestService(rf.getFriendshipRequestRepository(),friendshipService);
        this.networkService = new NetworkService(friendshipService,userService);
        this.messageService = new MessageService(rf.getMessageRepository());

    }
    public static ServiceFacade getInstance(){
        if(instance == null){
            instance = new ServiceFacade();
        }
        return instance;
    }

    public void setScreenService(ScreenService screenService) {
        this.screenService = screenService;
    }

    public void switchScene(String name) {
        this.screenService.switchScene(name);
    }

    public Iterable<User> getAllUsers() {
        return userService.getAll();
    }

    public Optional<User> getUserById(Long id) {
        return userService.getById(id);
    }

    public Optional<User> getUserByName(String name) {
        return userService.getByUsername(name);
    }

    public boolean login(String username, String password) {
        return userService.login(username, password);
    }
    public Optional<User> createUser(User user) {
        return userService.create(user);
    }

    public Optional<Friendship> getFriendshipById(Tuple<Long,Long> id){
        return friendshipService.getById(id);
    }

    public List<Friendship> getUserFriends(Long id) {
        return friendshipService.getUserFriends(id);
    }

    public Optional<Friendship> createFriendship(Friendship fr){
        return friendshipService.create(fr);
    }

    public Optional<Friendship> deleteFriendship(Tuple<Long,Long> id){
        return friendshipService.delete(id);
    }


    public Iterable<FriendshipRequest> getRequestsForUser(User user){
     return friendshipRequestService.getRequestsForUser(user);
    }

    public void removeFriendshipRequest(FriendshipRequest fr){
        friendshipRequestService.removeRequest(fr.getSender(), fr.getReceiver());
    }
    public void createFriendshipRequest(User snd,User rcv){
        friendshipRequestService.addRequest(snd, rcv);
    }

    public List<Message> getMessages(Long sender,Long receiver){
        return messageService.getMessagesForConversation(sender, receiver);
    }
    public void addMessage(Long currentUser, Long chatterId, String message, Long reply) {
        Message msg = new Message(currentUser,chatterId,message,reply, LocalDateTime.now());
        messageService.addMessage(msg);
    }
    public Optional<Message> getMessageById(Long id){
        return messageService.getMessageById(id);
    }
    public void addObserver(Observer observer, ObservableType type) {
        if(type == ObservableType.REQUEST){
            friendshipRequestService.addObserver(observer);
        }
        if(type == ObservableType.USER){
            userService.addObserver(observer);
        }
        if(type == ObservableType.FRIENDSHIP){
            friendshipService.addObserver(observer);
        }
        if(type == ObservableType.MESSAGE){
            messageService.addObserver(observer);
        }
    }


}
