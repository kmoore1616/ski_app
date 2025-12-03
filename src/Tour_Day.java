import java.util.Date;

public class Tour_Day {
    private String location;
    private String conditions;
    private String avalanche_conditions;
    private int vertical;
    private String review;
    private String date;

    public Tour_Day(String date, String location, String conditions, String avalanche_conditions, int vertical, String review) {
        this.date = date;
        this.location = location;
        this.conditions = conditions;
        this.avalanche_conditions = avalanche_conditions;
        this.vertical = vertical;
        this.review = review;
    }


}
