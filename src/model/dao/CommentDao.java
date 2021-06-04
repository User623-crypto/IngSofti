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

    public Comment getCommentsByCourse(int courseId) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        Comment comment = null;
        PreparedStatement preparedStatement;

        if(connection == null)
            throw new SQLException("There is no established connection");

        String mysql = "SELECT * FROM comment WHERE course_id = ? " +
                "INNER JOIN user ON user.id = comment.id_user";
        preparedStatement = connection.prepareStatement(mysql);
        preparedStatement.setInt(1, courseId);

        rs = preparedStatement.executeQuery();

        if(rs.next())
        {
            comment = new Comment(rs.getInt("id_thread"), rs.getInt("id_course"), rs.getInt("id_user"), rs.getInt("comment_type"), rs.getString("comment_body"), rs.getInt("no_of_likes"));
            comment.setId(rs.getInt("id"));
            comment.setUser_name(rs.getString("name"));
        }

        preparedStatement.close();
        return comment;
    }

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

    public List<Comment> getRepliesByComment(int threadId) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        ResultSet rs;
        List<Comment> replies = new ArrayList<Comment>();
        PreparedStatement preparedStatement;

        Comment reply = null;

        if(connection == null)
            throw new SQLException("There is no connection established");

        String mysql = "SELECT * FROM comment WHERE id_thread = ? "+
                "INNER JOIN user ON user.id = comment.id_user";

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

