package ubb.scs.map.Utils.events;


import ubb.scs.map.Domain.User;

public class UserEntityChangeEvent implements Event {
    private final ChangeEventType type;
    private User data;
    private User oldData;

    public UserEntityChangeEvent(ChangeEventType type, User data) {
        this.type = type;
        this.data = data;
    }
    public UserEntityChangeEvent(ChangeEventType type, User data, User oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }
    public UserEntityChangeEvent(ChangeEventType type){
        this.type = type;

    }
    public ChangeEventType getType() {
        return type;
    }

    public User getData() {
        return data;
    }

    public User getOldData() {
        return oldData;
    }
}