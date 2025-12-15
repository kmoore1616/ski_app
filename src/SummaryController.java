import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

public class SummaryController {
    private ArrayList<SkiDay> days = new ArrayList<>();
    private SummaryView summaryView;
    private int total_vert;
    private int total_days;

    public SummaryController(SummaryView summaryView){
        this.summaryView=summaryView;
        summaryView.addChangeListenerVerticalSlider(new changeListenerVerticalSlider());
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

    public class changeListenerVerticalSlider implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            summaryView.setVerticalLabel(summaryView.getVertical_slider());
            updateContentArea();
        }

    }
}
