import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class SkiDayController {
    static ArrayList<SkiDay> days = new ArrayList<>();

    private SkiDayView skiDayView;
    private SkiModel skiModel;
    private final int RESORT = 0;
    private final int TOUR = 1;

    private int current_selection = RESORT;

    private void updateSkiDays(){
        days.clear();
        days = SkiModel.getAllDays(current_selection);
    }

    public SkiDayController(SkiDayView skiDayView) {
        this.skiDayView = skiDayView;
        skiModel = new SkiModel();
        skiDayView.addActionListenerEnterDay(new ActionListenerEnterDay());
        skiDayView.addActionListenerDialogSubmit(new ActionListenerSubmitDialog());
        skiDayView.addActionListenerDialogCancel(new ActionListenerCancelDialog());
        skiDayView.addActionListenerResort(new ActionListenerResort());
        skiDayView.addActionListenerTour(new ActionListenerTour());
        skiDayView.addActionListenerRefresh(new ActionListenerRefresh());
        skiDayView.addActionListenerDeleteEntry(new ActionListenerDeleteEntry());
        skiDayView.addActionListenerSubmitDelete(new ActionListenerSubmitDelete());
        skiDayView.addActionListenerCancelDelete(new ActionListenerCancelDelete());
        skiDayView.addActionListenerSkiDay(new ActionListenerSkiDay());
        skiDayView.addActionListenerSummary(new ActionListenerSummary());
        skiDayView.addActionListenerForecast(new ActionListenerForecast());
        skiDayView.addActionListenerPrintPage(new ActionListenerPrintPage());
        updateSkiDays();

        skiDayView.updateContentArea(days);
    }

    public class ActionListenerDeleteEntry implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            skiDayView.showDeleteDialog();
        }
    }

    public class ActionListenerCancelDelete implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            skiDayView.hideDeleteDialog();
            skiDayView.clearDeleteDialog();
        }
    }

    public class ActionListenerSubmitDelete implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                if(SkiModel.deleteEntryById(Integer.parseInt(skiDayView.getDelete_entry())) == 0){
                    skiDayView.errorPopup();
                }else{

                    skiDayView.deletePopup();
                    updateSkiDays();
                    skiDayView.updateContentArea(days);
                }
            } catch (SQLException | NumberFormatException e) {
                skiDayView.errorPopup();
                throw new RuntimeException(e);
            }
        }
    }

    public class ActionListenerRefresh implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            updateSkiDays();
            skiDayView.updateContentArea(days);
        }
    }

    public class ActionListenerResort implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            skiDayView.switchPanel(RESORT);
            current_selection = RESORT;
        }
    }


    public class ActionListenerTour implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            skiDayView.switchPanel(TOUR);
            current_selection = TOUR;
        }
    }

    public class ActionListenerEnterDay implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            skiDayView.showEntryDialog();
        }
    }

    public class ActionListenerCancelDialog implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            skiDayView.hideEntryDialog();
        }
    }

    public class ActionListenerSkiDay implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }
    public class ActionListenerSummary implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    public class ActionListenerForecast implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    public class ActionListenerPrintPage implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    public class ActionListenerSubmitDialog implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                SkiDay day = new SkiDay(
                        skiDayView.getDate_field(),
                        skiDayView.getCondition_field(),
                        skiDayView.getResortField(),
                        skiDayView.getRunsArea(),

                        Integer.parseInt(skiDayView.getVertical_field()),
                        skiDayView.getReviewArea()
                );
                SkiModel.insertDay(day);
                skiDayView.entryPopup();
                updateSkiDays();
                skiDayView.updateContentArea(days);

                skiDayView.clearEntryDialog();
                skiDayView.hideEntryDialog();
            } catch (RuntimeException e) {
                skiDayView.errorPopup();
                throw new RuntimeException(e);
            }

        }
    }
}
