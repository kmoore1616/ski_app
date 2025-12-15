import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView {
    private JPanel loginPanel;
    private JTextField username_field, password_field;
    private JButton login_button, signup_button;
    private JDialog error_dialog, login_success_dialog, signup_success_dialog;

    public LoginView(JFrame frame) {
        loginPanel = new JPanel();
        username_field = new JTextField(15);
        password_field = new JTextField(15);
        login_button = new JButton("Login");
        signup_button = new JButton("New Account");
        error_dialog = new JDialog(frame, "Error", true);
        login_success_dialog = new JDialog(frame, "Login Successful!", true);
        signup_success_dialog= new JDialog(frame, "Signup Successful!", true);

    }

    public void initUI() {
        // Center panel for fields
        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.add(new JLabel("Enter your Login information or create a new account"), BorderLayout.NORTH);
        userPanel.add(new JLabel("Username:"), BorderLayout.WEST);
        userPanel.add(username_field, BorderLayout.CENTER);

        JPanel passPanel = new JPanel(new BorderLayout());
        passPanel.add(new JLabel("Password:"), BorderLayout.WEST);
        passPanel.add(password_field, BorderLayout.CENTER);

        centerPanel.add(userPanel, BorderLayout.NORTH);
        centerPanel.add(passPanel, BorderLayout.SOUTH);

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(login_button, BorderLayout.CENTER);
        bottomPanel.add(signup_button, BorderLayout.SOUTH);

        loginPanel.add(centerPanel, BorderLayout.CENTER);
        loginPanel.add(bottomPanel, BorderLayout.SOUTH);

        login_success_dialog.add(new JLabel("Login Successful"));
        login_success_dialog.pack();

        signup_success_dialog.add(new JLabel("Signup Successful"));
        signup_success_dialog.pack();

        error_dialog.add(new JLabel("Something went wrong. Check your entry"));
        error_dialog.pack();
    }

    public void showErrorPopup(){
        error_dialog.setVisible(true);
    }

    public void showLoginPopup(){
        login_success_dialog.setVisible(true);
    }

    public void showSignupPopup(){
        signup_success_dialog.setVisible(true);
    }



    public JPanel getLoginPanel() {
        return loginPanel;
    }

    public String getUsername_field() {
        return username_field.getText();
    }

    public String  getPassword_field() {
        return password_field.getText();
    }

    public void addActionListenerLogin(ActionListener listener){login_button.addActionListener(listener);}
    public void addActionListenerSignup(ActionListener listener){signup_button.addActionListener(listener);}

}
