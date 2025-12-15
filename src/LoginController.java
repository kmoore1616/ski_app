import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class LoginController {
    private final int LOGIN = 1;
    private final int SIGNUP = 0;
    private LoginView view;
    private Runnable loginSuccess;
    public LoginController(LoginView view, Runnable loginSuccess) throws IOException {

        this.loginSuccess=loginSuccess;
        this.view = view;
        new BackendController();
        view.addActionListenerLogin(new ActionListenerLogin());
        view.addActionListenerSignup(new ActionListenerSignup());
    }

    public class ActionListenerSignup implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(view.getUsername_field().isEmpty() && view.getPassword_field().isEmpty()){
                view.showErrorPopup();
            }else {
                StringBuilder txMessage = new StringBuilder();
                txMessage.append(view.getUsername_field());
                txMessage.append(",");
                txMessage.append(view.getPassword_field());
                BackendController.sendDbCode(SIGNUP);
                if(BackendController.getDbCode() == '0'){
                    view.showSignupPopup();
                }else{
                    view.showErrorPopup();
                }
            }
        }
    }

    public class ActionListenerLogin implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(view.getUsername_field().isEmpty() && view.getPassword_field().isEmpty()){
                view.showErrorPopup();
            }else {
                StringBuilder txMessage = new StringBuilder();
                txMessage.append(view.getUsername_field());
                txMessage.append(",");
                txMessage.append(view.getPassword_field());
                try {
                    BackendController.sendDbCode(LOGIN);
                    if (BackendController.download_db(txMessage.toString()) == 1) {
                        view.showLoginPopup();
                        loginSuccess.run();
                    }else{
                        view.showErrorPopup();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
}
