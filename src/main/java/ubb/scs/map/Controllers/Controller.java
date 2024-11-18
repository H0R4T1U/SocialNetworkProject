package ubb.scs.map.Controllers;


import ubb.scs.map.Services.FriendshipService;
import ubb.scs.map.Services.ScreenService;
import ubb.scs.map.Services.UserService;

public interface Controller {
    void setUserService(UserService userService);
    void setScreenService(ScreenService screenService);
    void setFriendshipService(FriendshipService friendshipService);
    void init();
}
