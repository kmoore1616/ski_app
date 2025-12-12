import com.sun.nio.sctp.AssociationChangeNotification;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;

public class MasterView {
    private JFrame frame;
    private static ArrayList<SkiDay> days = new ArrayList<>();
    private JTabbedPane tabs;

    public MasterView() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        tabs = new JTabbedPane();

        SkiDayView skiDayView = new SkiDayView(frame);
        skiDayView.initUI();
        JPanel ski_day_panel = skiDayView.getSki_day_panel();

        SummaryView summaryView = new SummaryView();
        summaryView.initUI();
        JPanel summary_panel = summaryView.getSummary_panel();

        SkiDayController skiDayController = new SkiDayController(skiDayView);
        SummaryController summaryController = new SummaryController(summaryView);

        tabs.addTab("Enter Ski Day", ski_day_panel);
        tabs.addTab("Summary", summary_panel);
        frame.add(tabs);

        tabs.addChangeListener(new ChangeListener() {
           @Override
           public void stateChanged(ChangeEvent changeEvent) {
               summaryController.updateDays();
               skiDayController.updateSkiDays();
           }
       });


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(2400, 1200);
        frame.setVisible(true);

    }
}

