import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class Review_View {
    private JFrame frame;
    private JPanel toolbar_panel, button_panel, content_panel, main_panel;
    private JTextArea content_area;
    private JButton enter_day, refresh, ski_day_button, summary_button, forecast_button;
    private Image refresh_icon;

    private JDialog dialog;
    private JPanel dialog_input_panel, dialog_action_panel, dialog_button_panel, dialog_resort_panel, dialog_tour_panel;
    private JRadioButton resort_button, tour_button;
    private ButtonGroup ski_day_type_group;
    private JTextField dateField, location_field, condition_field, avy_field, vertical_field;
    private JTextField resort_field;
    private JTextArea review_area, runs_area;
    private JButton dialogSubmitButton, dialogCancelButton;
    private JScrollPane runs_scroll, review_scroll;


    private final int RESORT = 0;
    private final int TOUR = 1;

    public Review_View(){
        frame = new JFrame();
        toolbar_panel = new JPanel();
        button_panel = new JPanel();
        content_panel = new JPanel();
        main_panel = new JPanel();
        content_area = new JTextArea(50, 80);
        enter_day = new JButton("Enter Day");
        dialog_resort_panel = new JPanel();
        dialog_tour_panel = new JPanel();

        try{
            refresh_icon = ImageIO.read(getClass().getResource("resources/refresh.png"));
        } catch (IOException e) {
            System.out.println("Resource not found: refresh.png");
            refresh.setText("Refresh");
        }
        refresh = new JButton();
        ski_day_button = new JButton("Ski Day");
        summary_button = new JButton("Summary");
        forecast_button = new JButton("Forecast");

        // Dialog
        dialog = new JDialog(frame, "Enter Ski Day", true);

        // Buttons
        dialogSubmitButton = new JButton("Submit");
        dialogCancelButton = new JButton("Cancel");

        // Panels
        dialog_input_panel = new JPanel();
        dialog_action_panel = new JPanel();
        dialog_button_panel = new JPanel();

        // Radio Buttons
        resort_button = new JRadioButton("Resort Day");
        resort_button.setSelected(true);
        tour_button = new JRadioButton("Tour Day");
        ski_day_type_group = new ButtonGroup();

        // Inputs
        dateField = new JTextField(15);
        location_field  = new JTextField(15);
        condition_field = new JTextField(15);
        avy_field       = new JTextField(15);
        vertical_field  = new JTextField(15);
        resort_field    = new JTextField(15);

        // Text Areas
        review_area = new JTextArea(5, 20);
        review_area.setLineWrap(true);
        review_area.setWrapStyleWord(true);
        review_scroll = new JScrollPane(review_area);

        runs_area   = new JTextArea(5, 20);
        runs_area.setLineWrap(true);
        runs_area.setWrapStyleWord(true);
        runs_scroll = new JScrollPane(runs_area);
    }

    public void init_UI(){

        // Toolbar panel
        toolbar_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        toolbar_panel.add(ski_day_button);
        toolbar_panel.add(summary_button);
        toolbar_panel.add(forecast_button);

        toolbar_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        // Button Panel
        Border padding_border = BorderFactory.createEmptyBorder(5, 10, 5, 10);

        button_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        if(refresh_icon != null){
            refresh.setIcon(new ImageIcon(refresh_icon));
        }
        button_panel.add(enter_day);
        button_panel.add(refresh);
        button_panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK), // Bottom line
                padding_border // Padding inside
        ));


        // Content Panel
        content_area.setEditable(false);
        content_area.setFont(new Font("SansSerif", Font.PLAIN, 18));
        content_panel.setLayout(new BorderLayout());
        content_panel.add(content_area, BorderLayout.CENTER);

        // Final Setup
        main_panel.setLayout(new BorderLayout());
        main_panel.add(button_panel, BorderLayout.NORTH);
        main_panel.add(content_panel);

        frame.setLayout(new BorderLayout());
        frame.add(toolbar_panel, BorderLayout.NORTH);
        frame.add(main_panel);

        init_Dialog();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private void init_Dialog(){
        ski_day_type_group.add(resort_button);
        ski_day_type_group.add(tour_button);

        dialog_button_panel.add(resort_button);
        dialog_button_panel.add(tour_button);
        dialog_button_panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // We call switchPanel here to populate the center area initially
        switchPanel(RESORT);

        dialog_action_panel.add(dialogCancelButton);
        dialog_action_panel.add(dialogSubmitButton);
        dialog_action_panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        dialog.setLayout(new BorderLayout());
        dialog.add(dialog_button_panel, BorderLayout.NORTH);
        dialog.add(dialog_input_panel, BorderLayout.CENTER);
        dialog.add(dialog_action_panel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
    }

    public void switchPanel(int selection){
        dialog_input_panel.removeAll();

        // Setup generic constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components
        gbc.anchor = GridBagConstraints.WEST; // Left align components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        if(selection == RESORT) {
            dialog_resort_panel.removeAll(); // Clear to re-add shared components (Date/Review)

            gbc.gridx = 0; gbc.gridy = 0;
            dialog_resort_panel.add(new JLabel("Date: "), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            dialog_resort_panel.add(dateField, gbc);

            gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
            dialog_resort_panel.add(new JLabel("Resort: "), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            dialog_resort_panel.add(resort_field, gbc);

            gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
            dialog_resort_panel.add(new JLabel("Vertical Gained:"), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            dialog_resort_panel.add(vertical_field, gbc);

            gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
            dialog_resort_panel.add(new JLabel("Runs: "), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
            dialog_resort_panel.add(runs_scroll, gbc); // Add scroll pane, not area

            gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0; gbc.weighty = 0.0; gbc.fill = GridBagConstraints.HORIZONTAL;
            dialog_resort_panel.add(new JLabel("Review of Day:"), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
            dialog_resort_panel.add(review_scroll, gbc); // Add scroll pane

            dialog_input_panel.add(dialog_resort_panel, BorderLayout.CENTER);

        } else if(selection == TOUR){
            dialog_tour_panel.removeAll(); // Clear to re-add shared components

            gbc.gridx = 0; gbc.gridy = 0;
            dialog_tour_panel.add(new JLabel("Date: "), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            dialog_tour_panel.add(dateField, gbc);

            gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
            dialog_tour_panel.add(new JLabel("Location: "), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            dialog_tour_panel.add(location_field, gbc);

            gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
            dialog_tour_panel.add(new JLabel("Conditions: "), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            dialog_tour_panel.add(condition_field, gbc);

            gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
            dialog_tour_panel.add(new JLabel("Avalanche Conditions: "), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            dialog_tour_panel.add(avy_field, gbc);

            gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0;
            dialog_tour_panel.add(new JLabel("Vertical Gained:"), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            dialog_tour_panel.add(vertical_field, gbc);

            gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.0;
            dialog_tour_panel.add(new JLabel("Review of Day:"), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
            dialog_tour_panel.add(review_scroll, gbc);

            dialog_input_panel.add(dialog_tour_panel, BorderLayout.CENTER);
        }

        dialog_input_panel.revalidate();
        dialog_input_panel.repaint();
        dialog.pack();
    }

    /*
    public void switchPanel(int selection){
       dialog_input_panel.removeAll();
       if(selection == RESORT) {
           dialog_input_panel.add(dialog_resort_panel);
       }else if(selection == TOUR){
           dialog_input_panel.add(dialog_tour_panel);
       }
       dialog_input_panel.revalidate();
       dialog_input_panel.repaint();
    }
    */

    public void clear_dialog(){
        dateField.setText("");
        resort_field.setText("");
        runs_area.setText("");
        location_field.setText("");
        condition_field.setText("");
        avy_field.setText("");
        vertical_field.setText("");
        review_area.setText("");
        resort_button.setSelected(true);
        switchPanel(RESORT);
    }

    public void showDialog(){
        dialog.setVisible(true);
    }

    public void hideDialog(){
        dialog.setVisible(false);
    }

    public String getDateField() {
        return dateField.getText();
    }

    public String getLocation_field() {
        return location_field.getText();
    }

    public String getCondition_field() {
        return condition_field.getText();
    }

    public String getAvy_field() {
        return avy_field.getText();
    }

    public String getVertical_field() {
        return vertical_field.getText();
    }

    public String getResort_field() {
        return resort_field.getText();
    }

    public String getReview_area() {
        return review_area.getText();
    }

    public String getRuns_area() {
        return runs_area.getText();
    }

    public void addActionListenerEnterDay(ActionListener actionListener){enter_day.addActionListener(actionListener);}
    public void addActionListenerRefresh(ActionListener actionListener){refresh.addActionListener(actionListener);}
    public void addActionListenerSkiDay(ActionListener actionListener){ski_day_button.addActionListener(actionListener);}
    public void addActionListenerSummary(ActionListener actionListener){summary_button.addActionListener(actionListener);}
    public void addActionListenerForecast(ActionListener actionListener){forecast_button.addActionListener(actionListener);}
    public void addActionListenerDialogSubmit(ActionListener actionListener){dialogSubmitButton.addActionListener(actionListener);}
    public void addActionListenerDialogCancel(ActionListener actionListener) {dialogCancelButton.addActionListener(actionListener);}
    public void addActionListenerResort(ActionListener actionListener){resort_button.addActionListener(actionListener);}
    public void addActionListenerTour(ActionListener actionListener){tour_button.addActionListener(actionListener);}
}
