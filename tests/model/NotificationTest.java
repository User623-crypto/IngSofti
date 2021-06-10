package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    Notification notification;
    @BeforeEach
    void setUp() {
        notification = new Notification("This is a new notification");
    }
    @Test
    void setNotification() {
        notification.setNotification("Updated Notification");
        String expResult = "Updated Notification";
        assertEquals(notification.getNotification(),expResult);
    }
}