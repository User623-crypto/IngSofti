package model;

public class FriendRequest {
    private int id;
    private int userReceiverId;
    private int userSenderId;
    private User senderUser;
    private int state;


    /**
     * Constructor that will create a FriendRequest
     * @param id Its the DB parameter
     * @param userReceiverId The user who received the friend request
     * @param userSenderId The user id who sent the friend request
     * @param state the state of friend request 0 if it is active 1 if it is rejected
     */
    public FriendRequest(int id, int userReceiverId, int userSenderId, int state) {
        this.id = id;
        this.userReceiverId = userReceiverId;
        this.userSenderId = userSenderId;
        this.state = state;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(User senderUser) {
        this.senderUser = senderUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserReceiverId() {
        return userReceiverId;
    }

    public void setUserReceiverId(int userReceiverId) {
        this.userReceiverId = userReceiverId;
    }

    public int getUserSenderId() {
        return userSenderId;
    }

    public void setUserSenderId(int userSenderId) {
        this.userSenderId = userSenderId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
