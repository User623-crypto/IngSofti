package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    Course course;
    @BeforeEach
    void setUp() {
        course = new Course("Science Course",1,"6pm");
    }

    @Test
    void setName() {
        course.setName("Testing");
        String expResult = "Testing";
        assertEquals(course.getName(),expResult);
    }
}