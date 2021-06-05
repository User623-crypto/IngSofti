package model.dao;

import model.FriendRequest;
import model.Notification;
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

public class FriendRequestDao {

    /**
     * Get all friend requests for a user
     * @param userId
     * @return
     * @throws SQLException
     */
    public List<FriendRequest> getFriendsRequest(int userId) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        List<FriendRequest> friendRequestList = new ArrayList<>();
        PreparedStatement preparedStatement;

        if (connection==null)
            throw new SQLException("Can not establish connection");
        /**Sql code*/
        String mysql="select * from friend_request where user_receiver = ?;";
        preparedStatement=connection.prepareStatement(mysql);
        preparedStatement.setInt(1, userId);

        rs=preparedStatement.executeQuery();

        while (rs.next())
        {

            FriendRequest friendRequest = new FriendRequest(rs.getInt("id"),userId,
                    rs.getInt("user_sender"),rs.getInt("state"));

            friendRequestList.add(friendRequest);
        }

        preparedStatement.close();

        for (FriendRequest friends: friendRequestList
             ) {
            friends.setSenderUser(new UserDao().readUserById(friends.getUserSenderId()));

        }

        return friendRequestList;
    }

    public void sendFriendRequest(int senderId,int receiverId) throws SQLException,IllegalArgumentException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;

        if (connection == null) throw new SQLException("Connection is null");
        try{
            connection.setAutoCommit(false);


            String mysql="INSERT INTO friend_request"+
                    "(user_receiver,user_sender,state)"+
                    " VALUES(?,?,?)";
            preparedStatement = connection.prepareStatement(mysql);
            preparedStatement.setInt(1,receiverId);
            preparedStatement.setInt(2, senderId);
            preparedStatement.setInt(3, 0);

            int rowAffected = preparedStatement.executeUpdate();


            if (rowAffected == 1)
            {
                String sqlPivot = "INSERT INTO notification"+
                        "(user_main,active,notification)"+
                        " VALUES(?,?,?)";

                preparedStatement1=connection.prepareStatement(sqlPivot);
                preparedStatement1.setInt(1,receiverId);
                preparedStatement1.setInt(2, 1);
                preparedStatement1.setString(3, Session.userSession.getName()+" sent you a friend request");

                preparedStatement1.executeUpdate();


                connection.commit();

            } else connection.rollback();
        }catch (Exception e){
            connection.rollback();
            throw new IllegalArgumentException(e.getMessage());

        }finally {
            if(preparedStatement!=null) preparedStatement.close();
            if(preparedStatement1!=null) preparedStatement1.close();
            connection.setAutoCommit(true);

        }
    }

    /**
     * Checks if users are friend or not
     * @param userId1 the first user who will be befriended
     * @param userId2 the second user who will be befriended
     * @return return true if they are friend
     */
    public boolean areFriends(int userId1,int userId2)throws SQLException
    {
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        PreparedStatement preparedStatement;
        boolean areFriends = false;

        if (connection==null)
            throw new SQLException("Can not establish connection");
        String mysql="select * from friends where friend_user=? and friend_users=?;";
        preparedStatement=connection.prepareStatement(mysql);
        preparedStatement.setInt(1, userId1);
        preparedStatement.setInt(2, userId2);

        rs=preparedStatement.executeQuery();

        if (rs.next())
        {
            areFriends = true;
        }

        preparedStatement.close();

        return areFriends;
    }

    public void acceptFriendRequest(FriendRequest friendRequest) throws SQLException{

        Connection connection = DatabaseManager.getConnection();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;

        if (connection == null) throw new SQLException("Connection is null");

        /*Check if there are already friends*/
        if (areFriends(friendRequest.getUserReceiverId(), friendRequest.getUserSenderId()))
            return;

        try{
            connection.setAutoCommit(false);


            String mysql="INSERT INTO friends"+
                    "(friend_user,friend_users)"+
                    " VALUES(?,?)";
            preparedStatement = connection.prepareStatement(mysql);
            preparedStatement.setInt(1,friendRequest.getUserReceiverId());
            preparedStatement.setInt(2, friendRequest.getUserSenderId());

            int rowAffected = preparedStatement.executeUpdate();


            if (rowAffected == 1)
            {
                String sqlPivot = "INSERT INTO friends"+
                        "(friend_user,friend_users)"+
                        " VALUES(?,?)";

                preparedStatement1=connection.prepareStatement(sqlPivot);
                preparedStatement1.setInt(1, friendRequest.getUserSenderId());
                preparedStatement1.setInt(2,friendRequest.getUserReceiverId());


                int rowAffected1 = preparedStatement1.executeUpdate();
                /*Send notification when back to the request sender if the user accepted*/
                if (rowAffected1 == 1)
                {
                    String sqlPivot2 = "INSERT INTO notification"+
                            "(user_main,active,notification)"+
                            " VALUES(?,?,?)";

                    preparedStatement3=connection.prepareStatement(sqlPivot2);
                    preparedStatement3.setInt(1,friendRequest.getUserSenderId());
                    preparedStatement3.setInt(2, 1);
                    preparedStatement3.setString(3, Session.userSession.getName()+" accepted your  friend request");

                    preparedStatement3.executeUpdate();

                }else connection.rollback();

                String mysql2="DELETE FROM friend_request where user_receiver=? and user_sender=?";
                preparedStatement2 = connection.prepareStatement(mysql2);
                preparedStatement2.setInt(1,friendRequest.getUserReceiverId());
                preparedStatement2.setInt(2, friendRequest.getUserSenderId());

                connection.commit();



            } else connection.rollback();
        }catch (Exception e){
            connection.rollback();
            throw new IllegalArgumentException(e.getMessage());

        }finally {
            if(preparedStatement!=null) preparedStatement.close();
            if(preparedStatement1!=null) preparedStatement1.close();
            if(preparedStatement2!=null) preparedStatement2.close();
            if(preparedStatement3!=null) preparedStatement3.close();
            connection.setAutoCommit(true);



        }
    }

    public void rejectFriendRequest(FriendRequest friendRequest)throws  SQLException{
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;

        if (connection == null) throw new SQLException("Connection is null");
        try{
            connection.setAutoCommit(false);


            String mysql="DELETE FROM friend_request where user_receiver=? and user_sender=?";
            preparedStatement = connection.prepareStatement(mysql);
            preparedStatement.setInt(1,friendRequest.getUserReceiverId());
            preparedStatement.setInt(2, friendRequest.getUserSenderId());

            int rowAffected = preparedStatement.executeUpdate();


            if (rowAffected == 1)
            {
                String sqlPivot = "INSERT INTO notification"+
                        "(user_main,active,notification)"+
                        " VALUES(?,?,?)";

                preparedStatement1=connection.prepareStatement(sqlPivot);
                preparedStatement1.setInt(1,friendRequest.getUserSenderId());
                preparedStatement1.setBoolean(2, true);
                preparedStatement1.setString(3, Session.userSession.getName()+" rejected  your friend request");

                preparedStatement1.executeUpdate();


                connection.commit();

            } else connection.rollback();
        }catch (Exception e){
            connection.rollback();
            throw new IllegalArgumentException(e.getMessage());

        }finally {
            if(preparedStatement!=null) preparedStatement.close();
            if(preparedStatement1!=null) preparedStatement1.close();
            connection.setAutoCommit(true);

        }
    }

}
