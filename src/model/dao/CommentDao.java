package model.dao;

import model.Comment;
import model.Helpers;
import zdatabase.DatabaseManager;
import zextra.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class used by Comment model to
 */
public class CommentDao {

    /**
     * Method used to insert new comment into the database
     * @param comment Comment to be inserted into the database
     * @return Returns the comment after attaching the name property
     * @throws SQLException In case there's trouble connecting to the database
     * @throws IllegalArgumentException In case there's trouble creating the database record and the ID is not generated
     */
    public Comment insertIntoDB(Comment comment) throws SQLException, IllegalArgumentException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement preparedStatement;

        if(connection == null)
            throw new SQLException("There is no connection established");

        String mysql = "INSERT INTO comment "+
                "(id_thread, id_course, id_user, comment_type, comment_body) " +
                "VALUES (?,?,?,?,?);";
        preparedStatement = connection.prepareStatement(mysql, Statement.RETURN_GENERATED_KEYS);

        if(comment.getId_thread() == null)
            preparedStatement.setNull(1, Types.NULL);
        else
            preparedStatement.setInt(1, comment.getId_thread());
        if(comment.getId_course() == null)
            preparedStatement.setNull(2, Types.NULL);
        else
            preparedStatement.setInt(2, comment.getId_course());
        if(comment.getId_user() == null)
            preparedStatement.setNull(3, Types.NULL);
        else
            preparedStatement.setInt(3, comment.getId_user());
        preparedStatement.setInt(4, comment.getComment_type());
        preparedStatement.setString(5, comment.getComment_body());

        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs != null && rs.next()) {
            comment.setId(rs.getInt(1));
        }else throw new IllegalArgumentException("Cannot get the id");


        preparedStatement.close();

        comment.setUser_name(Session.userSession.getName());

        return comment;
    }

    /**
     * Gets the number of likes for a specific comment
     * @param commentId The ID of the comment
     * @return The number of likes
     * @throws SQLException In case there's trouble connecting to the database
     */
    public Integer getLikes(Integer commentId) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;

        PreparedStatement preparedStatement;

        if(connection == null)
            throw new SQLException("There is no established connection");

        String mysql = "SELECT COUNT(*) AS rowcount FROM likes WHERE id_comment = ?;";
        preparedStatement = connection.prepareStatement(mysql);
        preparedStatement.setInt(1, commentId);

        rs = preparedStatement.executeQuery();

        rs.next();
        int no_of_likes = rs.getInt("rowcount");


        preparedStatement.close();
        return no_of_likes;
    }

    /**
     * Performs the action of adding a like to a comment
     * @param commentId The ID of comment being liked
     * @param userId The ID of the user liking the comment
     * @throws SQLException In case there's trouble connecting to the database
     */
    public void addLikeToComment(Integer commentId, Integer userId) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement preparedStatement;

        if(connection == null)
            throw new SQLException("There is no established connection");

        String mysql = "INSERT INTO likes (id_comment, id_user) " +
                        "VALUES (?,?);";
        preparedStatement = connection.prepareStatement(mysql);
        preparedStatement.setInt(1, commentId);
        preparedStatement.setInt(2, userId);

        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    /**
     * Checks if a user has liked a comment previously
     * @param commentId The ID of the comment being checked
     * @param userId The ID of the user being checked
     * @return Returns true or false whether a person has liked the comment
     * @throws SQLException In case there's trouble connecting to the database
     */
    public boolean checkIfLiked(Integer commentId, Integer userId) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement preparedStatement;

        if(connection == null)
            throw new SQLException("There is no established connection");

        String mysql = "SELECT COUNT(*) AS rowcount " +
                        "FROM likes " +
                        "WHERE id_comment = ? AND id_user = ?;";
        preparedStatement = connection.prepareStatement(mysql);
        preparedStatement.setInt(1, commentId);
        preparedStatement.setInt(2, userId);

        ResultSet rs = preparedStatement.executeQuery();

        rs.next();
        boolean liked = rs.getInt("rowcount") == 0;

        preparedStatement.close();

        return !liked;
    }

    /**
     * Gets the list of comments
     * @param commentType The type of comment (Refer to Helpers class)
     * @param threadId The ID of the main comment - can be null
     * @param courseId The ID of the course on which the comment was made - can be null
     * @param userId THe ID of the user who has commented - can be null
     * @return Returns the list of comments
     * @throws SQLException In case there's trouble connecting to the database
     */
    public List<Comment> getComments(Integer commentType, Integer threadId, Integer courseId, Integer userId) throws SQLException{
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        List<Comment> comments = new ArrayList<Comment>();

        PreparedStatement preparedStatement = null;

        Comment comment = null;

        if(connection == null)
            throw new SQLException("There is no established connection");

        String mysql = "";
        // Get comments on a course
        if(commentType == Helpers.CommentType.BASIC_COMMENT.ordinal()) {
            mysql = "SELECT * FROM comment " +
                    "INNER JOIN user ON user.id = comment.id_user " +
                    "WHERE id_course = ? AND comment_type = ?;";
            preparedStatement = connection.prepareStatement(mysql);
            preparedStatement.setInt(1, courseId);
            preparedStatement.setInt(2, commentType);
        }
        // Get announcements on a course
        if(commentType == Helpers.CommentType.ANNOUNCEMENT.ordinal()) {
            mysql = "SELECT * FROM comment " +
                    "WHERE id_course = ? AND comment_type = ?;";
            preparedStatement = connection.prepareStatement(mysql);
            preparedStatement.setInt(1, courseId);
            preparedStatement.setInt(2, commentType);
        }
        // Get post updates by user
        if(commentType == Helpers.CommentType.POST_UPDATE.ordinal()) {
            mysql = "SELECT * FROM comment " +
                    "INNER JOIN user ON user.id = comment.id_user " +
                    "WHERE comment_type = ? AND id_user = ?;";
            preparedStatement = connection.prepareStatement(mysql);
            preparedStatement.setInt(1, commentType);
            preparedStatement.setInt(2, userId);
        }
        // Get replies on a comment
        if(commentType == Helpers.CommentType.REPLY.ordinal()){
            mysql = "SELECT * FROM comment "+
                    "INNER JOIN user ON user.id = comment.id_user "+
                    "WHERE id_thread = ?";
            preparedStatement = connection.prepareStatement(mysql);
            preparedStatement.setInt(1, threadId);
        }


        rs = preparedStatement.executeQuery();

        while(rs.next())
        {
            comment = new Comment(rs.getInt("id_thread"), rs.getInt("id_course"), rs.getInt("id_user"), rs.getInt("comment_type"), rs.getString("comment_body"));
            comment.setId(rs.getInt("id"));
            comment.setUser_name(rs.getString("name"));
            comments.add(comment);
        }

        preparedStatement.close();
        Collections.reverse(comments);
        return comments;

    }

}

