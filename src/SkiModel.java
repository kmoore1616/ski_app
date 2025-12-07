import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class SkiModel {
    private static Connection connection;

    public SkiModel(){
        try {
            connection = Database_Connection.getConnection();
            createTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<SkiDay> getAllDays(int dayType){
        ArrayList<SkiDay> list = new ArrayList<>();
        String getCMD = "SELECT id, date, conditions, location, runs, vertical, review FROM skiDays";

        try(Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(getCMD);

            while(rs.next()){
                list.add(new SkiDay(
                        rs.getInt("id"),
                        rs.getString("date"),
                        rs.getString("conditions"),
                        rs.getString("location"),
                        rs.getString("runs"),
                        rs.getInt("vertical"),
                        rs.getString("review")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static int deleteEntryById(int id) throws SQLException{
        String deleteCMD = "DELETE FROM skiDays WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(deleteCMD);
        statement.setInt(1, id);
        return statement.executeUpdate();
    }

    public static void insertDay(SkiDay day) {
        String insertCMD = "INSERT INTO skiDays " +
                "(date, location, conditions, runs, vertical, review) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertCMD)) {
            statement.setString(1, day.getDate());
            statement.setString(2, day.getConditions());
            statement.setString(3, day.getLocation());
            statement.setString(4, day.getRuns());
            statement.setInt(5, day.getVertical());
            statement.setString(6, day.getReview());
            statement.executeUpdate();
            System.out.println("Inserted Record");

        } catch (SQLException e) {
            System.out.println("Here!");
            throw new RuntimeException(e);
        }
    }


    public static void createTable(){
        String createCMD = """
                CREATE TABLE IF NOT EXISTS skiDays (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    date TEXT NOT NULL,
                    location TEXT NOT NULL,
                    conditions TEXT NOT NULL,
                    runs TEXT,
                    vertical INTEGER NOT NULL,
                    review TEXT NOT NULL)
                """;
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(createCMD);
            System.out.println("Table created Successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
