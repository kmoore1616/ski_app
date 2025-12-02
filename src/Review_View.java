import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Review_View {
    private JFrame frame;
    private JPanel toolbar_panel, button_panel, content_panel, main_panel;
    private JTextArea content_area;
    private JButton enter_day, refresh, ski_day_button, summary_button, forecast_button;
    private Image refresh_icon;


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
        System.out.println(enter_day.getHeight());
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

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);


    }

}
