package zdatabase;



import java.sql.*;

public class DatabaseManager {
    //public final static String url="jdbc:mysql://localhost:3306/magazine_v2";
//    public final static String url="jdbc:mysql://localhost/magazine_v2?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//    public final static String dbName="test";
//    public final static String dbUsername="root";
//    public final static String dbPassword="testing1234";
    public final static String url= Config.url;
    public final static String dbUsername=Config.dbUsername;
    public final static String dbPassword=Config.dbPassword;

    private static Connection connection = null;



    /**Krijon lidhjen me databazen*/
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
