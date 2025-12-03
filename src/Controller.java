import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controller {
    // Was originally against inheritance, but I would like a SkiDay object to hold everything in a list

    static ArrayList<Ski_Day> days = new ArrayList<>();

    private Review_View reviewView;
    private Ski_Model skiModel;
    private final int RESORT = 0;
    private final int TOUR = 1;

    private int current_selection = RESORT;

    public Controller(Review_View reviewView) {
        this.reviewView = reviewView;
        skiModel = new Ski_Model();
        reviewView.addActionListenerEnterDay(new ActionListenerEnterDay());
        reviewView.addActionListenerDialogSubmit(new ActionListenerSubmitDialog());
        reviewView.addActionListenerDialogCancel(new ActionListenerCancelDialog());
        reviewView.addActionListenerResort(new ActionListenerResort());
        reviewView.addActionListenerTour(new ActionListenerTour());
    }

    public class ActionListenerResort implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            reviewView.switchPanel(RESORT);
            current_selection = RESORT;
        }
    }


    public class ActionListenerTour implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            reviewView.switchPanel(TOUR);
            current_selection = TOUR;
        }
    }

    public class ActionListenerEnterDay implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            reviewView.showDialog();
        }
    }

    public class ActionListenerCancelDialog implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            reviewView.hideDialog();
        }
    }

    public class ActionListenerSubmitDialog implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            // Talk to mal
            Ski_Day day = null;
            if(current_selection == RESORT){
                day = new Resort_Day(
                       reviewView.getDateField(),
                       reviewView.getResort_field(),
                       reviewView.getRuns_area(),
                       Integer.parseInt(reviewView.getVertical_field()),
                        reviewView.getReview_area()
                );

            } else if (current_selection == TOUR) {
                day = new Tour_Day(
                        reviewView.getDateField(),
                        reviewView.getLocation_field(),
                        reviewView.getCondition_field(),
                        reviewView.getAvy_field(),
                        Integer.parseInt(reviewView.getVertical_field()),
                        reviewView.getReview_area()
                );
            }

            // When DB is implemented replace with updateDays method
            days.add(day);

            reviewView.clear_dialog();
            reviewView.hideDialog();
        }
    }
}
