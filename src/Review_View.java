import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class Review_View {
    private JFrame frame;
    private JPanel toolbar_panel, button_panel, content_panel, main_panel, dialog_input_panel, dialog_action_panel;
    private JPanel dialog_button_panel;
    private JTextArea content_area;
    private JButton enter_day, refresh, ski_day_button, summary_button, forecast_button;
    private Image refresh_icon;

    private JDialog dialog;
    private JRadioButton resort_button, tour_button;
    private ButtonGroup ski_day_type_group;
    private JTextField dateField, location_field, condition_field, avy_field, vertical_field;
    private JTextArea review_area, runs;
    private JTextField resort, vertical, review, snowField;
    private JButton dialogSubmitButton, dialogCancelButton;

    private String contents;

    public Review_View(){
        frame = new JFrame();
        toolbar_panel = new JPanel();
        button_panel = new JPanel();
        content_panel = new JPanel();
        main_panel = new JPanel();
        content_area = new JTextArea(50, 80);
        enter_day = new JButton("Enter Day");
        // Found this on https://stackoverflow.com/questions/4801386/how-do-i-add-an-image-to-a-jbutton
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
        dateField = new JTextField(10);
        snowField = new JTextField(10);
        dialogSubmitButton = new JButton("Submit");
        dialogCancelButton = new JButton("Cancel");
        dialog_input_panel = new JPanel(new GridLayout(2,2,10,10));
        dialog_action_panel = new JPanel();
        dialog_button_panel = new JPanel();
        resort_button = new JRadioButton("Resort Day");
        resort_button.setSelected(true);
        tour_button = new JRadioButton("Tour Day");
        ski_day_type_group = new ButtonGroup();

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

        dialog_input_panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        dialog_input_panel.add(new JLabel("Date: "));
        dialog_input_panel.add(dateField);
        dialog_input_panel.add(new JLabel(("Entry: ")));
        dialog_input_panel.add(snowField);

        dialog_action_panel.add(dialogSubmitButton);
        dialog_action_panel.add(dialogCancelButton);

        dialog.setLayout(new BorderLayout());
        dialog.add(dialog_button_panel, BorderLayout.NORTH);
        dialog.add(dialog_input_panel);
        dialog.add(dialog_action_panel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);

    }

    public void showDialog(){
        dateField.setText("");
        snowField.setText("");
        dialog.setVisible(true);
    }

    public void hideDialog(){
        dialog.setVisible(false);
    }

    public void addActionListenerEnterDay(ActionListener actionListener){enter_day.addActionListener(actionListener);}
    public void addActionListenerRefresh(ActionListener actionListener){refresh.addActionListener(actionListener);}
    public void addActionListenerSkiDay(ActionListener actionListener){ski_day_button.addActionListener(actionListener);}
    public void addActionListenerSummary(ActionListener actionListener){summary_button.addActionListener(actionListener);}
    public void addActionListenerForecast(ActionListener actionListener){forecast_button.addActionListener(actionListener);}
    public void addActionListenerDialogSubmit(ActionListener actionListener){dialogSubmitButton.addActionListener(actionListener);}
    public void addActionListenerDialogCancel(ActionListener actionListener){dialogCancelButton.addActionListener(actionListener);}
}
