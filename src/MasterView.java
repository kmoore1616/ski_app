import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/*
    This class Controls all the GUI elements via tabs
    In order to add print functionality for all tabs would I need to add a toolbar in this view and then
    master controller that would handle the print? This seems wrong.

 */
public class MasterView {
    private JFrame frame;
    private JTabbedPane tabs;

    // Did this on a whim, but can easily be removed if bad practice
    private Font content_font = new Font("OpenSans", Font.PLAIN, 34);
    private Font button_font = new Font("OpenSans", Font.PLAIN, 28);

    public MasterView() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        tabs = new JTabbedPane();

        SkiDayView skiDayView = new SkiDayView(frame, content_font, button_font); // I needed to pass frame as the dialogs needed a reference
        skiDayView.initUI();
        JPanel ski_day_panel = skiDayView.getSki_day_panel();

        SummaryView summaryView = new SummaryView(content_font, button_font);
        summaryView.initUI();
        JPanel summary_panel = summaryView.getSummary_panel();

        SkiDayController skiDayController = new SkiDayController(skiDayView);
        SummaryController summaryController = new SummaryController(summaryView);

        tabs.addTab("Enter Ski Day", ski_day_panel);
        tabs.addTab("Summary", summary_panel);
        frame.add(tabs);

        // Is this bad practice? Keeps each arraylist up to date
        tabs.addChangeListener(new ChangeListener() {
           @Override
           public void stateChanged(ChangeEvent changeEvent) {
               summaryController.updateDays();
               skiDayController.updateSkiDays();
           }
       });

        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

    }
}

