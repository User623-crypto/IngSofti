package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FriendRequestTest {

    FriendRequest friendRequest;
    @BeforeEach
    void setUp() {
        friendRequest = new FriendRequest(1,2,1,1);

    }

    @Test
    void setSenderUser() {
        friendRequest.setSenderUser(new User("User1","Pass1"));
        User expUser = new User("User1","Pass1");
        assertEquals(expUser.getName(),friendRequest.getSenderUser().getName());
    }
}