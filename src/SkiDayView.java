import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class SkiDayView {
    private JFrame frame;
    private JPanel button_panel, content_panel, main_panel, update_dialog_panel, ski_day_panel, update_action_panel;
    private JPanel dialog_input_panel, dialog_action_panel, dialog_button_panel, dialog_resort_panel, dialog_tour_panel;
    private JButton enter_day, refresh, delete_entry_button, update_button;
    private JButton print_page_button, submit_update_button, cancel_update_button;
    private Image refresh_icon;
    private JDialog entry_dialog, update_dialog;
    private JRadioButton resort_button, tour_button;
    private ButtonGroup ski_day_type_group;
    private JTextField date_field, location_field, condition_field, avy_field, vertical_field;
    private JTextField resort_field;
    private JTextArea review_area, runs_area;
    private JButton dialog_submit_button, dialog_cancel_button;
    private JScrollPane runs_scroll, review_scroll, content_scroll;
    private DefaultListModel<SkiDay> dayListModel;
    private JList<SkiDay> dayList;

    private final int RESORT = 0;
    private final int TOUR = 1;


    public SkiDayView(JFrame frame){
        // Review GUI
        this.frame=frame;
        button_panel = new JPanel();
        content_panel = new JPanel();
        main_panel = new JPanel();
        enter_day = new JButton("Enter Day");
        delete_entry_button = new JButton("Delete Entry");
        dayListModel = new DefaultListModel<>();
        dayList = new JList<>(dayListModel);
        content_scroll = new JScrollPane(dayList);
        ski_day_panel = new JPanel();
        update_button = new JButton("Update Entry");
        submit_update_button = new JButton("Submit");
        cancel_update_button = new JButton("Cancel");
        update_action_panel = new JPanel();
        dayList.setSelectedIndex(0);

        try{
            refresh_icon = ImageIO.read(getClass().getResource("resources/refresh.png"));
        } catch (IOException e) {
            System.out.println("Resource not found: refresh.png");
            refresh.setText("Refresh");
        }

        refresh = new JButton();
        print_page_button = new JButton("Print Page");

        // Update Dialog
        update_dialog = new JDialog(frame, "Update Ski Day", true);
        update_dialog_panel = new JPanel();

        // Entry Dialog
        entry_dialog = new JDialog(frame, "Enter Ski Day", true);
        dialog_resort_panel = new JPanel();
        dialog_tour_panel = new JPanel();

        // Buttons
        dialog_submit_button = new JButton("Submit");
        dialog_cancel_button = new JButton("Cancel");

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
        date_field = new JTextField(15);
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

    public void initUI(){
        dayList.setFont(new Font("OpenSans", Font.PLAIN, 34));

        // Button Panel
        Border padding_border = BorderFactory.createEmptyBorder(5, 10, 5, 10);

        button_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        if(refresh_icon != null){
            refresh.setIcon(new ImageIcon(refresh_icon));
        }
        button_panel.add(enter_day);
        button_panel.add(delete_entry_button);
        button_panel.add(update_button);
        button_panel.add(refresh);
        button_panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK), // Bottom line
                padding_border // Padding inside
        ));


        // Focus dialog
        /*focus_dialog.setLayout(new BorderLayout());
        focus_area.setEditable(false);
        focus_area.setFont(new Font("OpenSans", Font.PLAIN, 30));
        focus_dialog_panel.setLayout(new BorderLayout());
        focus_dialog_panel.add(focus_scroll);
        focus_dialog.add(focus_dialog_panel);
        focus_dialog.setLocationRelativeTo(frame);
        focus_dialog.pack();

         */

        // Content Panel
        dayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        content_panel.setLayout(new BorderLayout());
        content_panel.add(content_scroll);

        // Final Setup
        main_panel.setLayout(new BorderLayout());
        main_panel.add(button_panel, BorderLayout.NORTH);
        main_panel.add(content_panel);

        initDialog();

        update_action_panel.add(cancel_update_button);
        update_action_panel.add(submit_update_button);
        update_action_panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        update_dialog.add(update_action_panel, BorderLayout.SOUTH);
        update_dialog.add(dialog_resort_panel);
        update_dialog.setLocationRelativeTo(frame);
        update_dialog.pack();


        ski_day_panel.setLayout(new BorderLayout());
        ski_day_panel.add(main_panel);



    }

    private void initDialog(){

        ski_day_type_group.add(resort_button);
        ski_day_type_group.add(tour_button);

        dialog_button_panel.add(resort_button);
        dialog_button_panel.add(tour_button);
        dialog_button_panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // We call switchPanel here to populate the center area initially
        switchPanel(RESORT);

        dialog_action_panel.add(dialog_cancel_button);
        dialog_action_panel.add(dialog_submit_button);
        dialog_action_panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        entry_dialog.setLayout(new BorderLayout());
        entry_dialog.add(dialog_button_panel, BorderLayout.NORTH);
        entry_dialog.add(dialog_input_panel, BorderLayout.CENTER);
        entry_dialog.add(dialog_action_panel, BorderLayout.SOUTH);

        entry_dialog.pack();
        entry_dialog.setLocationRelativeTo(frame);
    }

    //
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
            dialog_resort_panel.add(date_field, gbc);

            gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
            dialog_resort_panel.add(new JLabel("Resort: "), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            dialog_resort_panel.add(resort_field, gbc);

            gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
            dialog_resort_panel.add(new JLabel("Vertical Gained:"), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            dialog_resort_panel.add(vertical_field, gbc);


            gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
            dialog_resort_panel.add(new JLabel("Conditions: "), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            dialog_resort_panel.add(condition_field, gbc);

            gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0;
            dialog_resort_panel.add(new JLabel("Runs: "), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
            dialog_resort_panel.add(runs_scroll, gbc); // Add scroll pane, not area

            gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.0; gbc.weighty = 0.0; gbc.fill = GridBagConstraints.HORIZONTAL;
            dialog_resort_panel.add(new JLabel("Review of Day:"), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
            dialog_resort_panel.add(review_scroll, gbc); // Add scroll pane

            dialog_input_panel.add(dialog_resort_panel, BorderLayout.CENTER);

        } else if(selection == TOUR){
            dialog_tour_panel.removeAll(); // Clear to re-add shared components

            gbc.gridx = 0; gbc.gridy = 0;
            dialog_tour_panel.add(new JLabel("Date: "), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            dialog_tour_panel.add(date_field, gbc);

            gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
            dialog_tour_panel.add(new JLabel("Location: "), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            dialog_tour_panel.add(location_field, gbc);

            gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
            dialog_tour_panel.add(new JLabel("Vertical Gained:"), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            dialog_tour_panel.add(vertical_field, gbc);

            gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
            dialog_tour_panel.add(new JLabel("Conditions: "), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            dialog_tour_panel.add(condition_field, gbc);

            gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0;
            dialog_tour_panel.add(new JLabel("Runs: "), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
            dialog_tour_panel.add(runs_scroll, gbc); // Add scroll pane, not area


            gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.0;
            dialog_tour_panel.add(new JLabel("Review of Day:"), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
            dialog_tour_panel.add(review_scroll, gbc);

            dialog_input_panel.add(dialog_tour_panel, BorderLayout.CENTER);
        }

        dialog_input_panel.revalidate();
        dialog_input_panel.repaint();
        entry_dialog.pack();
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

    public void errorPopup(){
        JOptionPane.showMessageDialog(null, "Something went wrong, double check your submission...",
                "Message,", JOptionPane.INFORMATION_MESSAGE);
    }

    public void deletePopup(){
        JOptionPane.showMessageDialog(null, "Entry Successfully Deleted!", "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    public void entryPopup(){
        JOptionPane.showMessageDialog(null, "Entry Successfully Inserted!", "Message", JOptionPane.INFORMATION_MESSAGE);

    }

    public void showUpdateDialog(){
        update_dialog.setVisible(true);
    }

    public void hideUpdateDialog(){
        update_dialog.setVisible(false);
    }

    public void clearEntryDialog(){
        date_field.setText("");
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

    //public SkiDay(int id, String date, String conditions, String location, String runs, int vertical, String review) {
    public void updateContentArea(ArrayList<SkiDay> days){
        dayListModel.clear();
        for(int i=0; i<days.size(); i++){
            dayListModel.add(i, days.get(i));
        }
    }

    public JPanel getSki_day_panel() {
        return ski_day_panel;
    }

    public void showEntryDialog(){
        entry_dialog.setVisible(true);
    }

    public void hideEntryDialog(){
        entry_dialog.setVisible(false);
    }

    public String getDate_field() {
        return date_field.getText();
    }

    public String getLocation_field() {
        return location_field.getText();
    }

    public String getCondition_field() {
        return condition_field.getText();
    }

    public String getVertical_field() {return vertical_field.getText();}

    public String getResortField() {
        return resort_field.getText();
    }

    public String getReviewArea() {
        return review_area.getText();
    }

    public String getRunsArea() {
        return runs_area.getText();
    }

    public JList<SkiDay> getDayList() {
        return dayList;
    }

    //date field, resort_field, vertical_field, condition_field, runs_area, review_area


    public void setDate_field(String date) {
        this.date_field.setText(date);
    }

    public void setResort_field(String location) {
        this.resort_field.setText(location);
    }

    public void setVertical_field(String vertical) {
        this.vertical_field.setText(vertical);
    }

    public void setCondition_field(String condition) {
        this.condition_field.setText(condition);
    }

    public void setRuns_area(String runs) {
        this.runs_area.setText(runs);
    }

    public void setReview_area(String review) {
        this.review_area.setText(review);
    }

    public void addListListenerDayList(ListSelectionListener listener){dayList.addListSelectionListener(listener);}
    public void addActionListenerDeleteEntry(ActionListener actionListener){delete_entry_button.addActionListener(actionListener);}
    public void addActionListenerEnterDay(ActionListener actionListener){enter_day.addActionListener(actionListener);}
    public void addActionListenerUpdateEntry(ActionListener actionListener){update_button.addActionListener(actionListener);}
    public void addActionListenerRefresh(ActionListener actionListener){refresh.addActionListener(actionListener);}
    public void addActionListenerDialogSubmit(ActionListener actionListener){dialog_submit_button.addActionListener(actionListener);}
    public void addActionListenerDialogCancel(ActionListener actionListener) {dialog_cancel_button.addActionListener(actionListener);}
    public void addActionListenerResort(ActionListener actionListener){resort_button.addActionListener(actionListener);}
    public void addActionListenerTour(ActionListener actionListener){tour_button.addActionListener(actionListener);}
    public void addActionListenerPrintPage(ActionListener actionListener){print_page_button.addActionListener(actionListener);}
    public void addActionListenerCancelUpdate(ActionListener actionListener){cancel_update_button.addActionListener(actionListener);}
    public void addActionListenerSubmitUpdate(ActionListener actionListener){submit_update_button.addActionListener(actionListener);}
}
