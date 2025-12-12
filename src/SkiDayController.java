import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class SkiDayController {
    private static ArrayList<SkiDay> days = new ArrayList<>();

    private SkiDayView skiDayView;
    private int selectedDayId;

    public void updateSkiDays(){
        days.clear();
        days = SkiModel.getAllDays();
    }

    public SkiDayController(SkiDayView skiDayView) {
        this.skiDayView = skiDayView;
        new SkiModel(); // Run constructor
        skiDayView.addActionListenerEnterDay(new ActionListenerEnterDay());
        skiDayView.addActionListenerDialogSubmit(new ActionListenerSubmitDialog());
        skiDayView.addActionListenerDialogCancel(new ActionListenerCancelDialog());
        skiDayView.addActionListenerRefresh(new ActionListenerRefresh());
        skiDayView.addActionListenerPrintPage(new ActionListenerPrintPage());
        skiDayView.addActionListenerDeleteEntry(new ActionListenerDeleteEntry());
        skiDayView.addListListenerDayList(new listListenerDay());
        skiDayView.addActionListenerUpdateEntry(new ActionListenerUpdateEntry());
        skiDayView.addActionListenerCancelUpdate(new ActionListenerCancelUpdate());
        skiDayView.addActionListenerSubmitUpdate(new ActionListenerSubmitUpdate());

        updateSkiDays();
        skiDayView.updateContentArea(days);
    }

    public class listListenerDay implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            SkiDay tempday = null;
            if(!listSelectionEvent.getValueIsAdjusting()){
                tempday = skiDayView.getDayList().getSelectedValue();
                if(tempday != null){
                    selectedDayId = tempday.getId();
                }
            }

        }
    }

    public class ActionListenerDeleteEntry implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                SkiModel.deleteEntryById(selectedDayId);
                updateSkiDays();
                skiDayView.updateContentArea(days);
                skiDayView.deletePopup();
            } catch (RuntimeException|SQLException e) {
                skiDayView.errorPopup();
                throw new RuntimeException(e);
            }
        }
    }

    // This is almost useless, but its implemented so why not
    public class ActionListenerRefresh implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            updateSkiDays();
            skiDayView.updateContentArea(days);
        }
    }

    @Deprecated // Dont know if this is the correct convention, but I want to keep these around in case I build out tour/resort days
    public class ActionListenerResort implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
        }
    }

    @Deprecated
    public class ActionListenerTour implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
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

    // TODO
    public class ActionListenerPrintPage implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    public class ActionListenerCancelUpdate implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            skiDayView.clearEntryDialog();
            skiDayView.hideUpdateDialog();

        }
    }

    public class ActionListenerUpdateEntry implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            SkiDay temp = skiDayView.getDayList().getSelectedValue();
            skiDayView.setDate_field(temp.getDate());
            skiDayView.setCondition_field(temp.getConditions());
            skiDayView.setLocation_field(temp.getLocation());
            skiDayView.setReview_area(temp.getReview());
            skiDayView.setVertical_field(String.valueOf(temp.getVertical()));
            skiDayView.setRuns_area(temp.getRuns());
            skiDayView.showUpdateDialog();
        }
    }


    //public SkiDay(String date, String conditions, String location, String runs, int vertical, String review) {
    public class ActionListenerSubmitUpdate implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                SkiDay day = new SkiDay(
                        skiDayView.getDate_field(),
                        skiDayView.getCondition_field(),
                        skiDayView.getLocation_field(),
                        skiDayView.getRunsArea(),
                        Integer.parseInt(skiDayView.getVertical_field()),
                        skiDayView.getReviewArea()
                );
                SkiModel.updateDay(skiDayView.getDayList().getSelectedValue().getId(), day);

                updateSkiDays();
                skiDayView.updateContentArea(days);
                skiDayView.updatePopup();

                skiDayView.hideUpdateDialog();
            } catch (RuntimeException |SQLException e) {
                skiDayView.errorPopup();
                throw new RuntimeException(e);
            }

        }
    }



    public class ActionListenerSubmitDialog implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                SkiDay day = new SkiDay(
                        skiDayView.getDate_field(),
                        skiDayView.getCondition_field(),
                        skiDayView.getLocation_field(),
                        skiDayView.getRunsArea(),

                        Integer.parseInt(skiDayView.getVertical_field()),
                        skiDayView.getReviewArea()
                );
                SkiModel.insertDay(day);
                skiDayView.entryPopup();
                updateSkiDays();
                skiDayView.updateContentArea(days);

                skiDayView.hideEntryDialog();
            } catch (RuntimeException e) {
                skiDayView.errorPopup();
                throw new RuntimeException(e);
            }

        }
    }
}
