import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database_Connection {
    private static final String URI = "jdbc:sqlite:database.db";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(URI);
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }

}
