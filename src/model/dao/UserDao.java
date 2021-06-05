package model.dao;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import model.Course;
import model.Post;
import model.User;
import zdatabase.DatabaseManager;
import zextra.Session;

public class UserDao {

    /**
     * Insert the user into the database
     *
     * @param user the user that will be inserted
     * @throws SQLException if there is an error in closing the statement
     */
    public void insertIntoDB(User user) throws SQLException, IllegalArgumentException {
        Connection connectionM = DatabaseManager.getConnection();
        PreparedStatement preparedStatement;

        if (connectionM == null)
            throw new SQLException("There is no connection established");

        /**Should add code if exist throw error**/
        User a = readUserByName(user.getName());
        if (a!=null) {
            throw new IllegalArgumentException("This used does not exist");

        }
        String mysql = "INSERT INTO user" +
                "(name,password,notification,img)" +
                " VALUES(?,?,?,?)";
        preparedStatement = connectionM.prepareStatement(mysql,Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setBoolean(3, user.isNotification_on());
        preparedStatement.setString(4, user.getImageFile().getName());
        preparedStatement.executeUpdate();


        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs != null && rs.next()) {
            user.setId(rs.getInt(1));
        }else throw new IllegalArgumentException("Cannot get the id");



        preparedStatement.close();


    }

    /**
     *
     * @param name Then name that will search the database
     * @return It will get the user from the database and it will return it
     * @throws SQLException
     */
    public User readUserByName(String name) throws SQLException
    {
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        User user = null;
        PreparedStatement preparedStatement;

        if (connection==null)
            throw new SQLException("Can not establish connection");
        /**Sql code*/
        String mysql="select * from user where name = ?;";
        preparedStatement=connection.prepareStatement(mysql);
        preparedStatement.setString(1, name);

        rs=preparedStatement.executeQuery();

        if (rs.next())
        {
           user=new User(rs.getString("name"), rs.getString("password"));
            user.setNotification_on(rs.getBoolean("notification"));
            user.setId(rs.getInt("id"));
            user.setImageFile(new File(rs.getString("img")));
        }

        preparedStatement.close();

        return user;
    }

    public User readUserById(int id) throws SQLException
    {
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        User user = null;
        PreparedStatement preparedStatement;

        if (connection==null)
            throw new SQLException("Can not establish connection");
        /**Sql code*/
        String mysql="select * from user where id = ?;";
        preparedStatement=connection.prepareStatement(mysql);
        preparedStatement.setInt(1, id);

        rs=preparedStatement.executeQuery();

        if (rs.next())
        {
            user=new User(rs.getString("name"), rs.getString("password"));
            user.setNotification_on(rs.getBoolean("notification"));
            user.setId(rs.getInt("id"));
            user.setImageFile(new File(rs.getString("img")));
        }

        preparedStatement.close();

        return user;
    }

    /**
     * It will update the user
     * @param user The modified user that will be updated
     * @throws SQLException
     */
    public void updateFromDb(User user)throws SQLException
    {
        Connection connection = DatabaseManager.getConnection();
        String sql = "UPDATE user set name = ?, password = ?, notification = ?, img = ? where id = ?;";
        if (connection == null)
            throw new SQLException("Cannot establish connection with the database");

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setBoolean(3, user.isNotification_on());
        preparedStatement.setString(4, user.getImageFile().getName());
        preparedStatement.setInt(5, user.getId());
        preparedStatement.executeUpdate();

    }

    public boolean doesExist(String name) throws SQLException
    {
        boolean doesExist;
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        PreparedStatement preparedStatement;

        if (connection==null)
            throw new SQLException("Can not establish connection");
        /**Sql code*/
        String mysql="select * from user where name = ?;";
        preparedStatement=connection.prepareStatement(mysql);
        preparedStatement.setString(1, name);

        rs=preparedStatement.executeQuery();

        if (rs.next()) doesExist = true;
        else doesExist = false;


        preparedStatement.close();

        return doesExist;
    }

    /**
     * This function checks if the user is enrolled in a course.
     * @param userId
     * @param courseId
     * @return true if it is enrolled and false if it isnt
     * @throws SQLException There may be a problem with connection since it is outside db
     */
    public boolean isEnrolled(int userId,int courseId) throws SQLException {
        boolean doesExist;
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        PreparedStatement preparedStatement;

        if (connection==null)
            throw new SQLException("Can not establish connection");
        /**Sql code*/
        String mysql="select * from user_course where user_id=? and course_id=?;";
        preparedStatement=connection.prepareStatement(mysql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, courseId);

        rs=preparedStatement.executeQuery();

        if (rs.next()) doesExist = true;
        else doesExist = false;


        preparedStatement.close();

        return doesExist;
    }

    public void insertUserIntoCourse(int userId, Course course) throws SQLException
    {
        Connection connectionM = DatabaseManager.getConnection();
        PreparedStatement preparedStatement;

        if (connectionM == null)
            throw new SQLException("There is no connection established");

        String mysql = "INSERT INTO user_course" +
                "(user_id,course_id)" +
                " VALUES(?,?)";
        preparedStatement = connectionM.prepareStatement(mysql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, course.getId());


        preparedStatement.executeUpdate();


        preparedStatement.close();

        new NotificationDao().sendNotificationToFriends(Session.userSession.getName()+" has enrolled in "+
                course.getName());
        Post newPost = new Post(0,"Wow this "+course.getName()+" is Amaizing");
        newPost.setPost_user(Session.userSession.getId());
        new PostDao().insertIntoDB(newPost);

    }

    public void deleteUserFromCourse(int userId,int courseId) throws SQLException{
        Connection connectionM = DatabaseManager.getConnection();
        PreparedStatement preparedStatement;

        if (connectionM == null)
            throw new SQLException("There is no connection established");

        String mysql = "delete from user_course where user_id=? and course_id=?";
        preparedStatement = connectionM.prepareStatement(mysql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, courseId);


        preparedStatement.executeUpdate();


        preparedStatement.close();
    }

    public List<User> readAllUserFriends(int userId)throws SQLException
    {
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        List <User> users = new ArrayList<>();
        PreparedStatement preparedStatement;

        if (connection==null)
            throw new SQLException("Can not establish connection");

        String mysql="select friend.* from friends\n" +
                " join user as main  on friend_user = main.id \n" +
                " join user as friend on friend_users =friend.id \n" +
                " where main.id = ?";
        preparedStatement=connection.prepareStatement(mysql);
        preparedStatement.setInt(1, userId);


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


}
