package model.dao;

import model.Post;
import model.User;
import zdatabase.DatabaseManager;
import zextra.Session;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDao {
    /**
     * This function inserts a post into database
     * @param post the post that will be inserted
     * @throws SQLException Error with the connection
     */
    public void insertIntoDB(Post post) throws SQLException {
        Connection connectionM = DatabaseManager.getConnection();
        PreparedStatement preparedStatement;

        if (connectionM == null)
            throw new SQLException("There is no connection established");


        String mysql = "INSERT INTO post" +
                "(Text,post_user)" +
                " VALUES(?,?)";
        preparedStatement = connectionM.prepareStatement(mysql);
        preparedStatement.setString(1, post.getText());
        preparedStatement.setInt(2, post.getPost_user());
        preparedStatement.executeUpdate();


        preparedStatement.close();


    }

    /**
     * This method inserts a Post and returns the post that it inserted from the database
     * @param post the new post that will be inserted
     * @return Returns a Post object
     * @throws SQLException If there is problem with the connection
     * @throws IllegalArgumentException Bad id argument
     */
    public Post insertPostAndReturn(Post post) throws SQLException, IllegalArgumentException {
        Connection connectionM = DatabaseManager.getConnection();
        PreparedStatement preparedStatement;


        if (connectionM == null)
            throw new SQLException("There is no connection established");


        String mysql = "INSERT INTO post" +
                "(Text,post_user)" +
                " VALUES(?,?)";

        preparedStatement = connectionM.prepareStatement(mysql,Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, post.getText());
        preparedStatement.setInt(2, post.getPost_user());
        preparedStatement.executeUpdate();
        preparedStatement.executeUpdate();


        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs != null && rs.next()) {
            post.setId(rs.getInt(1));
        }else throw new IllegalArgumentException("Cannot get the id");



        preparedStatement.close();
        return post;


    }

    /**
     * It reads a list of post from the db from a certain user
     * @param userId The id of the user which post will be read
     * @return It returns a List of Posts
     * @throws SQLException Error connection with the database
     */

    public List<Post> readPostsFromUser(int userId) throws SQLException{
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        List <Post> posts = new ArrayList<>();
        PreparedStatement preparedStatement;

        if (connection==null)
            throw new SQLException("Can not establish connection");

        String mysql="select * from post where post_user=?";
        preparedStatement=connection.prepareStatement(mysql);
        preparedStatement.setInt(1, userId);


        rs=preparedStatement.executeQuery();

        while (rs.next())
        {
            Post post=new Post(rs.getInt("likeNumber"), rs.getString("Text"));
            post.setPost_user(userId);
            post.setId(rs.getInt("id"));
            posts.add(post);
        }

        preparedStatement.close();

        return posts;
    }

    /**
     * This function updates post's like in the db
     * @param post_id Post id
     * @param likeNumber the number of likes that will be added
     * @throws SQLException Error connection with the database
     */
    public void updateLikes(int post_id,int likeNumber)throws SQLException
    {
        Connection connection = DatabaseManager.getConnection();
        String sql = "UPDATE post set likeNumber = likeNumber+ ? where id = ?;";
        if (connection == null)
            throw new SQLException("Cannot establish connection with the database");

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, likeNumber);
        preparedStatement.setInt(2,post_id );
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

}
