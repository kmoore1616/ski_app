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


    public static ArrayList<SkiDay> getAllDays(){
        ArrayList<SkiDay> list = new ArrayList<>();
        String getCMD = "SELECT id, date, conditions, location, runs, vertical, review FROM skiDays";

        try(Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(getCMD);
            //public SkiDay(int id, String date, String conditions, String location, String runs, int vertical, String review) {
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


    public static void deleteEntryById(int id) throws SQLException {
        String deleteCMD = "DELETE FROM skiDays WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(deleteCMD);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public static void updateDay(int id, SkiDay d) throws SQLException {
        String updateCMD = "UPDATE skiDays SET date = ?, location = ?, runs=?, vertical=?, review=? WHERE id=?";

        PreparedStatement statement = connection.prepareStatement(updateCMD);
        statement.setString(1, d.getDate());
        statement.setString(2, d.getLocation());
        statement.setString(3, d.getRuns());
        statement.setInt(4, d.getVertical());
        statement.setString(5, d.getReview());
        statement.setInt(6, id);
        statement.executeUpdate();

    }

    public static void insertDay(SkiDay day) {
        String insertCMD = "INSERT INTO skiDays " +
                "(date, conditions, location, runs, vertical, review) VALUES (?, ?, ?, ?, ?, ?)";
        //public SkiDay(String date, String conditions, String location, String runs, int vertical, String review) {


        try (PreparedStatement statement = connection.prepareStatement(insertCMD)) {
            statement.setString(1, day.getDate());
            statement.setString(2, day.getLocation());
            statement.setString(3, day.getConditions());
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
