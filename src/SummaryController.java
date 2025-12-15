import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SummaryController {
    private ArrayList<SkiDay> days = new ArrayList<>();
    private SummaryView summaryView;
    private int total_vert;
    private int total_days;

    public SummaryController(SummaryView summaryView){
        this.summaryView=summaryView;
        summaryView.addChangeListenerVerticalSlider(new ChangeListenerVerticalSlider());
        summaryView.addActionListenerPrint(new ActionListenerPrint());
        updateDays();
    }

    public void updateDays(){
        days.clear();
        days = SkiModel.getAllDays();
        updateContentArea();
        summaryView.setStats(total_days, total_vert);
    }

    // Single Responsibility!
    private void updateContentArea(){
        String content = "";
        total_vert = 0;
        total_days = 0;
        for(SkiDay day : days){
            total_days++;
            total_vert += day.getVertical();
            if(day.getVertical() > summaryView.getVertical_slider()){
                content += (day);
                content += "\n----------------------------------------------------------------\n";
            }
        }
        summaryView.setContent_area(content);


    }

    // https://www.geeksforgeeks.org/java/fileoutputstream-in-java/
    public class ActionListenerPrint implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try (FileOutputStream fos = new FileOutputStream("summary.tsv")){
                StringBuilder output = new StringBuilder();
                output.append("Date\tConditions\tLocation\tVertical\tRuns\tReview\n");
                for(SkiDay day : days){
                    output.append(day.getDate().strip() + "\t");
                    output.append(day.getConditions().strip() + "\t");
                    output.append(day.getLocation().strip() + "\t");
                    output.append(day.getVertical() + "\t");
                    output.append(day.getRuns().strip().replace('\n', ' ') + "\t");
                    output.append(day.getReview().strip().replace('\n', ' ') + "\n");
                }
                // https://www.geeksforgeeks.org/java/convert-string-to-byte-array-in-java-using-getbytescharset-method/
                byte[] writeArray = String.valueOf(output).getBytes(StandardCharsets.UTF_8);
                fos.write(writeArray);
                System.out.println("File Written Successfully");
                summaryView.showPrintDialog();
            }catch (IOException e){
                System.out.println("Error on file write");

            }

        }
    }

    public class ChangeListenerVerticalSlider implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            summaryView.setVerticalLabel(summaryView.getVertical_slider());
            updateContentArea();
        }

    }
}
