package ubb.scs.map.Controllers;

import ubb.scs.map.Services.FriendshipRequestService;
import ubb.scs.map.Services.FriendshipService;
import ubb.scs.map.Services.ScreenService;
import ubb.scs.map.Services.UserService;

import java.util.stream.StreamSupport;

public class ControllerSuperclass implements Controller{
    private UserService userService;
    private ScreenService screenService;
    private FriendshipService friendshipService;
    private FriendshipRequestService friendshipRequestService;

    public FriendshipRequestService getFriendshipRequestService() {
        return friendshipRequestService;
    }

    public void setFriendshipRequestService(FriendshipRequestService friendshipRequestService) {
        this.friendshipRequestService = friendshipRequestService;
    }

    public ScreenService getScreenService() {
        return screenService;
    }

    public UserService getUserService() {
        return userService;
    }

    public FriendshipService getFriendshipService() {
        return friendshipService;
    }

    public void setFriendshipService(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    public void setScreenService(ScreenService sceneService) {
        this.screenService = sceneService;
    }


    public void init() {

    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
