package model.dao;

import model.Course;
import model.User;
import zdatabase.DatabaseManager;
import zextra.Session;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

    public List<Course> readAllCourses() throws  SQLException{
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        List <Course> courses = new ArrayList<>();
        PreparedStatement preparedStatement;

        if (connection==null)
            throw new SQLException("Can not establish connection");

        String mysql="select * from course";
        preparedStatement=connection.prepareStatement(mysql);


        rs=preparedStatement.executeQuery();

        while (rs.next())
        {
            Course course = new Course(rs.getString("name"),rs.getInt("dita"),rs.getString("orari"));
            course.setId(rs.getInt("id"));
            courses.add(course);
        }

        preparedStatement.close();

        return courses;
    }
    public Course readCourse(int id) throws SQLException
    {
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        Course course = null;
        PreparedStatement preparedStatement;

        if (connection==null)
            throw new SQLException("Can not establish connection");
        String mysql="select * from course where id = ?;";
        preparedStatement=connection.prepareStatement(mysql);
        preparedStatement.setInt(1, id);

        rs=preparedStatement.executeQuery();

        if (rs.next())
        {
            course = new Course(rs.getString("name"),rs.getInt("dita"),rs.getString("orari"));
            course.setId(rs.getInt("id"));
        }

        preparedStatement.close();

        return course;
    }

    /**
     * Returns all the user from a course
     * @param course
     * @return
     * @throws SQLException
     */
    public List<User> readUsersInCourse(Course course) throws SQLException
    {
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        List <User> users = new ArrayList<>();
        PreparedStatement preparedStatement;

        if (connection==null)
            throw new SQLException("Can not establish connection");

        String mysql="select user.* from user_course\n" +
                "join user on user_id = user.id\n" +
                " where course_id =? and user_id!=?";
        preparedStatement=connection.prepareStatement(mysql);
        preparedStatement.setInt(1, course.getId());
        preparedStatement.setInt(2, Session.userSession.getId());


        rs=preparedStatement.executeQuery();

        while (rs.next())
        {
            User user=new User(rs.getString("name"), rs.getString("password"));
            user.setNotification_on(rs.getBoolean("notification"));
            user.setId(rs.getInt("id"));
            user.setImageFile(new File(rs.getString("img")));
            users.add(user);
        }

        preparedStatement.close();

        return users;
    }

    public static List<Course> readCoursesByDay(int weekdayOrdinal) throws SQLException
    {
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        List<Course> courses = new ArrayList<>();
        Course course = null;
        PreparedStatement preparedStatement;

        if (connection==null)
            throw new SQLException("Can not establish connection");
        String mysql="select * from course where dita = ?;";
        preparedStatement=connection.prepareStatement(mysql);
        preparedStatement.setInt(1, weekdayOrdinal);

        rs=preparedStatement.executeQuery();

        while (rs.next())
        {
            course = new Course(rs.getString("name"),rs.getInt("dita"),rs.getString("orari"));
            course.setId(rs.getInt("id"));
            courses.add(course);
        }

        preparedStatement.close();

        return courses;
    }

    public static List<Course> getCoursesByUser(int userId) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        List<Course> courses = new ArrayList<>();
        Course course = null;
        PreparedStatement preparedStatement;

        if (connection==null)
            throw new SQLException("Can not establish connection");
        String mysql="select * from user_course " +
                "INNER JOIN course ON course.id = user_course.course_id " +
                "where user_id = ?;";
        preparedStatement=connection.prepareStatement(mysql);
        preparedStatement.setInt(1, userId);

        rs=preparedStatement.executeQuery();

        while (rs.next())
        {
            course = new Course(rs.getString("name"),rs.getInt("dita"),rs.getString("orari"));
            course.setId(rs.getInt("id"));
            courses.add(course);
        }

        preparedStatement.close();

        return courses;
    }

}
