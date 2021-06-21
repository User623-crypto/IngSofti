package zdatabase;

/**
 * Database configurations
 */
public class Config {
    public final static String dbName="student1";
    public final static String url="jdbc:mysql://localhost/"+Config.dbName+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public final static String dbUsername="root";
    public final static String dbPassword="password";
}
