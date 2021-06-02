package model.dao;

import java.sql.*;

import model.User;
import zdatabase.DatabaseManager;

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
            throw new IllegalArgumentException("Nuk mund te egzistoj ky user");

        }
        String mysql = "INSERT INTO user" +
                "(name,password,notification)" +
                " VALUES(?,?,?)";
        preparedStatement = connectionM.prepareStatement(mysql);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setBoolean(3, user.isNotification_on());


        preparedStatement.executeUpdate();


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
        String sql = "UPDATE user set name = ?, password = ?, notification = ? where id = ?;";
        if (connection == null)
            throw new SQLException("Cannot establish connection with the database");

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setBoolean(3, user.isNotification_on());
        preparedStatement.setInt(4, user.getId());
        preparedStatement.executeUpdate();

    }
}
