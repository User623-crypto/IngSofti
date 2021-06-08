package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    Post post;
    @BeforeEach
    void setUp() {
        post = new Post(0,"This is the post");
    }

    @Test
    void setText() {
        post.setText("This is the new text");
        String expResult = "This is the new text";
        assertEquals(expResult,post.getText());
    }


}