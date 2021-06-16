package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    Comment comment;
    @BeforeEach
    void setUp() {
        comment = new Comment(1,1,1,1,"Comment");
    }

    @Test
    void setComment_body() {
        comment.setComment_body("Wow");
        String expResult = "Wow";
        assertEquals(comment.getComment_body(),expResult);
    }
}