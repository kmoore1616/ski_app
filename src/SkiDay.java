import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SkiDay{
    private int id;
    private String location;
    private String runs;
    private int vertical;
    private String conditions;
    private String review;
    private String date;

    public SkiDay(String date, String conditions, String location, String runs, int vertical, String review) {
        this.date = date;
        this.location= location;
        this.conditions = conditions;
        this.runs = runs;
        this.vertical = vertical;
        this.review = review;
    }

    public SkiDay(int id, String date, String conditions, String location, String runs, int vertical, String review) {
        this.id = id;
        this.date = date;
        this.location = location;
        this.conditions = conditions;
        this.runs = runs;
        this.vertical = vertical;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public String getConditions() {
        return conditions;
    }

    public String getLocation() {
        return location;
    }

    public String getRuns() {
        return runs;
    }

    public int getVertical() {
        return vertical;
    }

    public String getReview() {
        return review;
    }

    public String getDate() {
        return date;
    }

    public void prepare_insert_statement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, getDate());
        preparedStatement.setString(2, getConditions());
        preparedStatement.setString(3, getLocation());
        preparedStatement.setString(4, getRuns());
        preparedStatement.setInt(5, getVertical());
        preparedStatement.setString(6, getReview());

    }
}
