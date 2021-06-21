package model.dao;

import model.Notification;
import model.User;
import zdatabase.DatabaseManager;
import zextra.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDao {

    /**
     * It sets notification active status to 0 in Database
     * @param notification The notification that will be updated
     * @throws SQLException If there are errors in connectivity
     */
    public void dismissNotification(Notification notification)throws SQLException{
        Connection connection = DatabaseManager.getConnection();
        String sql = "UPDATE notification set active = ? where id = ?;";
        if (connection == null)
            throw new SQLException("Cannot establish connection with the database");

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setBoolean(1, false);
        preparedStatement.setInt(2, notification.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();

    }


    /**
     * Gets all active notification for an User
     * @param userId the userId where it gets the notification
     * @return Returns the notification list
     * @throws SQLException Throws exception if there is no connection
     */
    public List<Notification> getNotifications(int userId) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        List<Notification> notificationList = new ArrayList<>();
        PreparedStatement preparedStatement;

        if (connection==null)
            throw new SQLException("Can not establish connection");
        String mysql="select * from notification where user_main = ? and active=?;";
        preparedStatement=connection.prepareStatement(mysql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setBoolean(2, true);

        rs=preparedStatement.executeQuery();

        while (rs.next())
        {

            Notification notification = new Notification(rs.getString("notification"));
            notification.setActive(rs.getBoolean("active"));
            notification.setUserId(userId);
            notification.setId(rs.getInt("id"));
            notificationList.add(notification);
        }

        preparedStatement.close();

        return notificationList;
    }

    /**
     * Send notification to all users friend. The user is taken from session
     * @param text The inner text of the notification
     * @throws SQLException Throws exception if there is no connection
     */
    public void sendNotificationToFriends(String text)throws SQLException{

        List<User> users = new UserDao().readAllUserFriends(Session.userSession.getId());

        Connection connectionM = DatabaseManager.getConnection();
        PreparedStatement preparedStatement;

        if (connectionM == null)
            throw new SQLException("There is no connection established");

        String mysql = "INSERT INTO notification" +
                "(user_main,active,notification)" +
                " VALUES(?,?,?)";
        preparedStatement = connectionM.prepareStatement(mysql);

        for (User user:users) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setBoolean(2, true);
            preparedStatement.setString(3, text);

            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();
        preparedStatement.close();
    }
}
