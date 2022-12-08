package bankUI;

import Utils.Logger;
import Utils.SessionMgr;
import bankBackend.User;
import bankBackend.UserManager;
import bankUI.StockUI.StockHomeUI;

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
//
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.pack();
//        button234Button.addActionListener(e -> {
//            textField1.setText("" + cnt++);
//            label2.setText("bbb");
//            UserManager.getInstance().userLogin(this.textField1.getText(), this.textField2.getText());
//        });
    }

    public static void initGUI(String[] args) {

        JFrame frame = new BankGUI("bank");
        frame.setSize(1000,700);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        User user = SessionMgr.getSession().data.getUser();
        frame.setContentPane(new StockHomeUI(user.getId()));
        frame.getContentPane().add(new LoggerUI(5));
        frame.setVisible(true);
    }
}
