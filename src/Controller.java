import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Review_View reviewView;
    private Ski_Model skiModel;

    public Controller(Review_View reviewView) {
        this.reviewView = reviewView;
        skiModel = new Ski_Model();
        reviewView.addActionListenerEnterDay(new ActionListenerEnterDay());
        reviewView.addActionListenerDialogSubmit(new ActionListenerSubmitDialog());
        reviewView.addActionListenerDialogCancel(new ActionListenerCancelDialog());
    }

    public class ActionListenerEnterDay implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            reviewView.showDialog();
        }
    }

    public class ActionListenerCancelDialog implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            reviewView.hideDialog();
        }
    }

    public class ActionListenerSubmitDialog implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            reviewView.hideDialog();
        }
    }
}
