package bankUI;

import Utils.Logger;
import bankBackend.User;

import javax.swing.*;

public class BankGUI extends JFrame {
    private static Logger logHandle;
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton button234Button;
    private JTextField textField2;
    private JLabel label1;
    private JLabel label2;
    private int cnt = 0;

    public BankGUI(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        button234Button.addActionListener(e -> {
            textField1.setText("" + cnt++);
            label2.setText("bbb");
            // example to talk to the backend for a simulated login event
            // User.userLogin(this.textField1.getText(), this.textField2.getText());
        });
    }

    public static void initGUI(String[] args) {

        JFrame frame = new BankGUI("bank");

        frame.setVisible(true);
    }
}
