import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class SkiDayController {
    // Was originally against inheritance, but I would like a SkiDay object to hold everything in a list
    static ArrayList<SkiDay> days = new ArrayList<>();

    private ReviewView reviewView;
    private SkiModel skiModel;
    private final int RESORT = 0;
    private final int TOUR = 1;

    private int current_selection = RESORT;

    private void updateSkiDays(){
        days.clear();
        days = SkiModel.getAllDays(current_selection);
    }

    public SkiDayController(ReviewView reviewView) {
        this.reviewView = reviewView;
        skiModel = new SkiModel();
        reviewView.addActionListenerEnterDay(new ActionListenerEnterDay());
        reviewView.addActionListenerDialogSubmit(new ActionListenerSubmitDialog());
        reviewView.addActionListenerDialogCancel(new ActionListenerCancelDialog());
        reviewView.addActionListenerResort(new ActionListenerResort());
        reviewView.addActionListenerTour(new ActionListenerTour());
        reviewView.addActionListenerRefresh(new ActionListenerRefresh());
        reviewView.addActionListenerDeleteEntry(new ActionListenerDeleteEntry());
        reviewView.addActionListenerSubmitDelete(new ActionListenerSubmitDelete());
        reviewView.addActionListenerCancelDelete(new ActionListenerCancelDelete());
        updateSkiDays();
        reviewView.updateContentArea(days);
    }

    public class ActionListenerDeleteEntry implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            reviewView.showDeleteDialog();
        }
    }

    public class ActionListenerCancelDelete implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            reviewView.hideDeleteDialog();
            reviewView.clearDeleteDialog();
        }
    }

    public class ActionListenerSubmitDelete implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                if(SkiModel.deleteEntryById(Integer.parseInt(reviewView.getDelete_entry())) == 0){
                    reviewView.errorPopup();
                }else{

                    reviewView.deletePopup();
                    updateSkiDays();
                    reviewView.updateContentArea(days);
                }
            } catch (SQLException | NumberFormatException e) {
                reviewView.errorPopup();
                throw new RuntimeException(e);
            }
        }
    }

    public class ActionListenerRefresh implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            updateSkiDays();
            reviewView.updateContentArea(days);
        }
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
            reviewView.showEntryDialog();
        }
    }

    public class ActionListenerCancelDialog implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            reviewView.hideEntryDialog();
        }
    }

    public class ActionListenerSubmitDialog implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                SkiDay day = new SkiDay(
                        reviewView.getDate_field(),
                        reviewView.getCondition_field(),
                        reviewView.getResortField(),
                        reviewView.getRunsArea(),

                        Integer.parseInt(reviewView.getVertical_field()),
                        reviewView.getReviewArea()
                );
                SkiModel.insertDay(day);
                reviewView.entryPopup();
                updateSkiDays();
                reviewView.updateContentArea(days);

                reviewView.clearEntryDialog();
                reviewView.hideEntryDialog();
            } catch (RuntimeException e) {
                reviewView.errorPopup();
                throw new RuntimeException(e);
            }

        }
    }
}
