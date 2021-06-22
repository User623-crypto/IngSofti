package easymock;

import model.Course;
import model.dao.CourseDao;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseDaoTest {

    @Test
    public void testReadCourse() throws Exception {
        Course c = new Course("TestCourse", 1, "14:00-15:00");
        c.setId(1);
        CourseDao courseDao = EasyMock.createMock(CourseDao.class);
        EasyMock.expect(courseDao.readCourse(c.getId())).andReturn(c);
        EasyMock.replay(courseDao);
        Course courseToGet = courseDao.readCourse(1);
        assertEquals(courseToGet.getName(), c.getName());
    }

    @Test
    public void testReadAllCourses() throws Exception {
        Course c1 = new Course("TestCourse1", 1, "14:00-15:00");
        c1.setId(1);
        Course c2 = new Course("TestCourse2", 3, "14:00-15:00");
        c2.setId(2);
        List<Course> courseList = new ArrayList<>();
        courseList.add(c1);
        courseList.add(c2);

        CourseDao courseDao = EasyMock.createMock(CourseDao.class);
        EasyMock.expect(courseDao.readAllCourses()).andReturn(courseList);
        EasyMock.replay(courseDao);
        List<Course> courseListToGet = courseDao.readAllCourses();
        assertEquals(courseListToGet, courseList);
    }

}
