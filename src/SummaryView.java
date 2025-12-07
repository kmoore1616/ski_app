import javax.swing.*;
import java.awt.*;

public class SummaryView {
    private JFrame frame;
    private JPanel toolbar_panel;
    private JButton ski_day_button, summary_button, forecast_button;

    public SummaryView(){
        frame = new JFrame();
        toolbar_panel = new JPanel();

        ski_day_button = new JButton("Ski Day");
        summary_button = new JButton("Summary");
        forecast_button = new JButton("Forecast");
    }

    public void initUI(){

        toolbar_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        toolbar_panel.add(ski_day_button);
        toolbar_panel.add(summary_button);
        toolbar_panel.add(forecast_button);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

        toolbar_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        frame.setLayout(new BorderLayout());
        frame.add(toolbar_panel, BorderLayout.NORTH);
    }
}
