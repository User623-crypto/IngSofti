package model.dao;

import model.Comment;
import zdatabase.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {

    /**
     * Insert an entry into comment table in db
     * @param comment The comment object
     * @throws SQLException throws error if there is no connection
     * @throws IllegalArgumentException throws error if there is an illegal argument
     */
    public void insertIntoDB(Comment comment) throws SQLException, IllegalArgumentException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement preparedStatement;

        if(connection == null)
            throw new SQLException("There is no connection established");

        String mysql = "INSERT INTO comment "+
                "(id_thread, id_course, id_user, comment_type, comment_body, no_of_likes) " +
                "VALUES (?,?,?,?,?,0);";
        preparedStatement = connection.prepareStatement(mysql);
        preparedStatement.setInt(1, comment.getId_thread());
        preparedStatement.setInt(2, comment.getId_course());
        preparedStatement.setInt(3, comment.getId_user());
        preparedStatement.setInt(4, comment.getComment_type());
        preparedStatement.setString(5, comment.getComment_body());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Reads from database a list of comments for a course
     * @param courseId The course id
     * @return List of comments
     * @throws SQLException throws error if there is no connection
     */
    public static List<Comment> getCommentsByCourse(Integer courseId) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        List<Comment> comments = new ArrayList<Comment>();

        PreparedStatement preparedStatement;

        Comment comment = null;

        if(connection == null)
            throw new SQLException("There is no established connection");

        String mysql = "SELECT * FROM comment " +
                "INNER JOIN user ON user.id = comment.id_user " +
                "WHERE id_course = ?";
        preparedStatement = connection.prepareStatement(mysql);
        preparedStatement.setInt(1, courseId);

        rs = preparedStatement.executeQuery();

        while(rs.next())
        {
            comment = new Comment(rs.getInt("id_thread"), rs.getInt("id_course"), rs.getInt("id_user"), rs.getInt("comment_type"), rs.getString("comment_body"), rs.getInt("no_of_likes"));
            comment.setId(rs.getInt("id"));
            comment.setUser_name(rs.getString("name"));
            comments.add(comment);
        }

        preparedStatement.close();
        return comments;
    }

    /**
     * Get likes number of a comment from db
     * @param commentId Comment id
     * @return returns the likes number
     * @throws SQLException throws error if there is no connection
     */
    public Integer getLikes(Integer commentId) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        int no_of_likes = 0;
        PreparedStatement preparedStatement;

        if(connection == null)
            throw new SQLException("There is no established connection");

        String mysql = "SELECT no_of_likes FROM comment WHERE id = ?;";
        preparedStatement = connection.prepareStatement(mysql);
        preparedStatement.setInt(1, commentId);

        rs = preparedStatement.executeQuery();

        if(rs.next())
        {
            no_of_likes = rs.getInt("no_of_likes");
        }

        preparedStatement.close();
        return no_of_likes;
    }

    /**
     * Update likes number of a comment in the database by 1
     * @param commentId comment id
     * @throws SQLException throws error if there is no connection
     */

    public void addLikeToComment(Integer commentId) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        PreparedStatement preparedStatement;

        int new_no_of_likes = getLikes(commentId) + 1;

        if(connection == null)
            throw new SQLException("There is no established connection");

        String mysql = "UPDATE comment SET no_of_likes=" + new_no_of_likes + " WHERE id = ?;";
        preparedStatement = connection.prepareStatement(mysql);
        preparedStatement.setInt(1, commentId);

        preparedStatement.executeQuery();

        preparedStatement.close();
    }

    /**
     * Get a list of replies for a certain comment
     * @param threadId the main comments
     * @return List of comments
     * @throws SQLException throws error if there is no connection
     */
    public static List<Comment> getRepliesByComment(Integer threadId) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        List<Comment> replies = new ArrayList<Comment>();
        PreparedStatement preparedStatement;

        Comment reply = null;

        if(connection == null)
            throw new SQLException("There is no connection established");

        String mysql = "SELECT * FROM comment "+
                "INNER JOIN user ON user.id = comment.id_user "+
                "WHERE id_thread = ?";

        preparedStatement = connection.prepareStatement(mysql);
        preparedStatement.setInt(1, threadId);
        rs = preparedStatement.executeQuery();

        while(rs.next()){
            reply = new Comment(rs.getInt("id_thread"), rs.getInt("id_course"), rs.getInt("id_user"), rs.getInt("comment_type"), rs.getString("comment_body"), rs.getInt("no_of_likes"));
            reply.setId(rs.getInt("id"));
            reply.setUser_name(rs.getString("name"));
            replies.add(reply);
        }

        preparedStatement.close();

        return replies;
    }

}

