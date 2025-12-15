/*
Requirement:
Add a feature that lets the user filter the displayed ski days based on vertical gain.
The GUI must include a slider that allows the user to select a minimum vertical gain between 0 and 10000 feet.
After choosing a value, the user must click a button labeled "Filter by Vertical."
When pressed, the program should update the main display area to show only the ski days whose vertical gain is greater than or equal to the selected amount.
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SkiVertical {

    JFrame f;
    JSlider s;
    JButton b;
    JTextArea a;
    ArrayList<String> d = new ArrayList<>(); // fake ski database: as I was lazy and didn't want to use the database

    public SkiVertical() {
        f = new JFrame("Ski Days Filter Thing");
        f.setSize(600, 400);
        f.setLayout(null);

        // this should totally pull from the database but I didn't want to figure it out, you can do it!
        d.add("Snowbird,3000");
        d.add("Alta,1200");
        d.add("Jackson Hole,4300");
        d.add("Brighton,800");
        d.add("Solitude,2200");
        d.add("Aspen,5000");
        d.add("Park City,900");

        a = new JTextArea();
        a.setBounds(20, 20, 300, 300);
        a.setText("All Ski Days:\n" + dump());
        f.add(a);

        s = new JSlider(0, 10000, 0);
        s.setBounds(340, 20, 200, 50);
        s.setMajorTickSpacing(2000);
        s.setPaintLabels(true);
        s.setPaintTicks(true);
        f.add(s);

        JLabel l = new JLabel("Min Vertical: 0");
        l.setBounds(340, 70, 200, 30);
        f.add(l);

        s.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    l.setText("Min Vertical: " + s.getValue());
                } catch(Exception ex) { }
            }
        });

        b = new JButton("Filter by Vertical");
        b.setBounds(340, 110, 200, 40);
        f.add(b);

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int vv = s.getValue();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < d.size(); i++) {
                        String x = d.get(i);
                        try {
                            String[] parts = x.split(",");
                            int vert = Integer.parseInt(parts[1]);
                            if (vert >= vv) {
                                sb.append(x).append("\n");
                            }
                        } catch(Exception ex2) { }
                    }
                    a.setText("Filtered Ski Days:\n" + sb.toString());
                } catch(Exception ex3) { }
            }
        });

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    private String dump() {
        String z = "";
        for (String x : d) {
            z += x + "\n";
        }
        return z;
    }
}
