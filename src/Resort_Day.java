import java.util.Date;

public class Resort_Day extends Ski_Day{
    private String resort;
    private String runs;
    private int vertical;
    private String review;
    private String date;

    public Resort_Day(String date, String resort, String runs, int vertical, String review) {
        this.date = date;
        this.resort = resort;
        this.runs = runs;
        this.vertical = vertical;
        this.review = review;
    }

}
