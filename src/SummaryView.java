import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SummaryView {
    private JPanel summary_panel, filter_panel, stats_panel, button_panel;
    private JSlider vertical_slider;
    private JLabel vertical_label, stats;
    private JScrollPane content_scroll;
    private JTextArea content_area;
    private Font content_font, button_font;

    public SummaryView(Font content_font, Font button_font){
        summary_panel = new JPanel();
        stats_panel = new JPanel();
        filter_panel = new JPanel();
        vertical_slider = new JSlider(0, 10000, 0);
        content_area = new JTextArea();
        content_scroll = new JScrollPane(content_area);
        stats = new JLabel();
        vertical_label = new JLabel();
        this.content_font = content_font;
        this.button_font = button_font;

    }

    public void initUI(){
        stats.setFont(content_font);
        content_area.setFont(content_font);
        content_area.setLineWrap(true);
        summary_panel.setLayout(new BorderLayout());
        summary_panel.add(content_scroll);

        stats_panel.setLayout(new BorderLayout());

        filter_panel.setLayout(null);
        filter_panel.setPreferredSize(new Dimension(250, 0));

        content_area.setBounds(20, 20, 300, 300);

        vertical_slider.setBounds(10, 20, 200, 50);
        vertical_slider.setMajorTickSpacing(2000);
        vertical_slider.setPaintLabels(true);
        vertical_slider.setPaintTicks(true);
        filter_panel.add(vertical_slider);

        vertical_label.setText("Min Vertical: 0");
        vertical_label.setBounds(10, 70, 200, 30);
        filter_panel.add(vertical_label);
        summary_panel.add(filter_panel, BorderLayout.EAST);

        stats_panel.add(stats);
        summary_panel.add(stats_panel, BorderLayout.NORTH);

    }

    public JPanel getSummary_panel() {
        return summary_panel;
    }


    public void setStats(int total_days, int total_vert) {
        this.stats.setText("Total Days Skied: " + total_days + " Total Vertical Feet: " + total_vert);
    }


    public void setVerticalLabel(int vertical){
        vertical_label.setText("Min Vertical: " + vertical + "\n");
    }

    public int getVertical_slider() {
        return vertical_slider.getValue();
    }

    public void setContent_area(String content) {
        content_area.setText(content);
    }

    public void addChangeListenerVerticalSlider(ChangeListener listener){vertical_slider.addChangeListener(listener);}

}
