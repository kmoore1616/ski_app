import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


/*
    Refactoring note: I'd like to refactor this class ST each of the dialogs are within their own classes
    There is no reason for them to be here, it just bloats this class, and makes it difficult to find the
    correct elements of each panel
 */

public class SkiDayView {
    // --- Main Application Components ---
    private JFrame frame;
    private JPanel toolbar_panel, button_panel, content_panel, main_panel, delete_entry_panel, print_panel;
    private JTextArea content_area;
    private JButton enter_day, refresh, ski_day_button, summary_button, forecast_button, delete_entry_button;
    private JButton delete_cancel, delete_inner_button, print_page_button;
    private Image refresh_icon;
    private JDialog entry_dialog, delete_dialog, print_dialog;
    private JPanel dialog_input_panel, dialog_action_panel, dialog_button_panel, dialog_resort_panel, dialog_tour_panel;
    private JRadioButton resort_button, tour_button;
    private ButtonGroup ski_day_type_group;
    private JTextField date_field, location_field, condition_field, avy_field, vertical_field;
    private JTextField resort_field, delete_entry;
    private JTextArea review_area, runs_area;
    private JButton dialog_submit_button, dialog_cancel_button;
    private JScrollPane runs_scroll, review_scroll, content_scroll;

    private final int RESORT = 0;
    private final int TOUR = 1;


    public SkiDayView(){
        // To switch screens can I have this return a panel object with everything, then add to frame in a master view
        // Call method from the master view? Each screen view does the same?

        // Review GUI
        frame = new JFrame();
        toolbar_panel = new JPanel();
        button_panel = new JPanel();
        content_panel = new JPanel();
        enter_day = new JButton("Enter Day");
        delete_entry_button = new JButton("Delete Entry");
        update_button = new JButton("Update Entry");
        // Style
        this.button_font = button_font;
        this.content_font = content_font;
        try{
            refresh_icon = ImageIO.read(getClass().getResource("resources/refresh.png"));
        } catch (IOException e) {
            System.out.println("Resource not found: refresh.png");
            refresh.setText("Refresh");
        }

        submit_update_button = new JButton("Submit");
        cancel_update_button = new JButton("Cancel");
        update_action_panel = new JPanel();


        refresh = new JButton();
        print_page_button = new JButton("Print Page");

        // Update Dialog
        update_dialog = new JDialog(frame, "Update Ski Day", true);

        // Entry Dialog
        entry_dialog = new JDialog(frame, "Enter Ski Day", true);

        // Buttons
        dialog_submit_button = new JButton("Submit");
        dialog_cancel_button = new JButton("Cancel");

        // Panels
        dialog_input_panel = new JPanel();
        dialog_action_panel = new JPanel();


        // Inputs
        date_field = new JTextField(15);
        location_field  = new JTextField(15);
        condition_field = new JTextField(15);
        vertical_field  = new JTextField(15);

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
        initFonts();

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


        // Content Panel
        dayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        content_panel.setLayout(new BorderLayout());
        content_panel.add(content_scroll);

        // Final Setup
        main_panel.setLayout(new BorderLayout());
        main_panel.add(button_panel, BorderLayout.NORTH);
        main_panel.add(content_panel);

        initDialog();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public void initFonts(){
        dayList.setFont(content_font);
        enter_day.setFont(button_font);
        delete_entry_button.setFont(button_font);
        update_button.setFont(button_font);
    }

    private void initDialog(){
        runs_area.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        review_area.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        //private JTextField date_field, location_field, condition_field, avy_field, vertical_field;
        dialog_input_panel.add(new JLabel("Date: "));
        dialog_input_panel.add(date_field);
        dialog_input_panel.add(new JLabel("Conditions: "));
        dialog_input_panel.add(condition_field);
        dialog_input_panel.add(new JLabel("Location: "));
        dialog_input_panel.add(location_field);
        dialog_input_panel.add(new JLabel("Vertical Gain: "));
        dialog_input_panel.add(vertical_field);
        dialog_input_panel.add(new JLabel("Runs: "));
        dialog_input_panel.add(runs_area);
        dialog_input_panel.add(new JLabel("Review: "));
        dialog_input_panel.add(review_area);

        dialog_action_panel.add(dialog_cancel_button);
        dialog_action_panel.add(dialog_submit_button);
        dialog_action_panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        entry_dialog.setLayout(new BorderLayout());
        entry_dialog.add(dialog_action_panel, BorderLayout.SOUTH);

        entry_dialog.setLocationRelativeTo(frame);

        update_action_panel.add(cancel_update_button);
        update_action_panel.add(submit_update_button);
        update_action_panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        update_dialog.add(update_action_panel, BorderLayout.SOUTH);
        update_dialog.setLocationRelativeTo(frame);
    }

    //


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

    public void updatePopup(){
        JOptionPane.showMessageDialog(null, "Entry Successfully Updated!", "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showUpdateDialog(){
        update_dialog.add(dialog_input_panel);
        update_dialog.pack();
        update_dialog.setVisible(true);

    }

    public void hideUpdateDialog(){
        update_dialog.setVisible(false);
    }

    public void clearEntryDialog(){
        date_field.setText("");
        runs_area.setText("");
        location_field.setText("");
        condition_field.setText("");
        vertical_field.setText("");
        review_area.setText("");
    }

    //public SkiDay(int id, String date, String conditions, String location, String runs, int vertical, String review) {
    public void updateContentArea(ArrayList<SkiDay> days){
        String content = "";
        for(SkiDay day : days){
            String id = String.valueOf(day.getId());
            String date = day.getDate();
            String location = day.getLocation();
            String conditions = day.getConditions();
            String runs = day.getRuns();
            String vertical = String.valueOf(day.getVertical());
            String reveiw = day.getReview();
            content += ("ID: " +id+ " Date: " + date  + " Location: " + location + " Conditions: " + conditions +" Vertical: "
                    + vertical+ "\nRuns: " + runs + "\nReview: " + reveiw + "\n\n");

        }
    }


    public void showEntryDialog(){
        clearEntryDialog();
        entry_dialog.add(dialog_input_panel, BorderLayout.CENTER);
        entry_dialog.pack();
        entry_dialog.setVisible(true);
    }

    public void hideEntryDialog(){
        entry_dialog.setVisible(false);
    }

    public String getDate_field() {
        return date_field.getText();
    }

    public JPanel getSki_day_panel() {
        return ski_day_panel;
    }
    public String getLocation_field() {
        return location_field.getText();
    }

    public String getCondition_field() {return condition_field.getText();}

    public String getVertical_field() {return vertical_field.getText();}

    public String getReviewArea() {
        return review_area.getText();
    }

    public String getRunsArea() {
        return runs_area.getText();
    }

    public JList<SkiDay> getDayList() {
        return dayList;
    }


    public void setDate_field(String date) {
        this.date_field.setText(date);
    }

    public void setLocation_field(String location) {
        this.location_field.setText(location);
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
    public void addActionListenerPrintPage(ActionListener actionListener){print_page_button.addActionListener(actionListener);}
    public void addActionListenerCancelUpdate(ActionListener actionListener){cancel_update_button.addActionListener(actionListener);}
    public void addActionListenerSubmitUpdate(ActionListener actionListener){submit_update_button.addActionListener(actionListener);}
}
