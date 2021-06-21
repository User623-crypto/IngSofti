package zdatabase;



import java.sql.*;

public class DatabaseManager {

    public final static String url= Config.url;
    public final static String dbUsername=Config.dbUsername;
    public final static String dbPassword=Config.dbPassword;

    private static Connection connection = null;


    /**
     * Creates a connection with the database if no previous connection was established
     * @return Returns the connection object
     */
    public static synchronized Connection getConnection()  {
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    connection =  DriverManager.getConnection(url,dbUsername,dbPassword);
                } catch (SQLException var2) {
                    System.out.println(var2.getMessage());
                    return null;
                }
            }
        } catch (SQLException throwables) {
            return null;
        }
        return connection;
    }




}
