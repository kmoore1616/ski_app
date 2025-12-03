import java.sql.Connection;
import java.sql.SQLException;

public class Ski_Model {
    private static Connection connection;

    public Ski_Model(){
        try {
            connection = Database_Connection.getConnection();
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
