package bankUI;

import javax.swing.*;

public class BankGUI extends JFrame {
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton button234Button;
    private JTextField textField2;
    private JLabel label1;
    private JLabel label2;
    private int cnt = 0;
    public BankGUI(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        button234Button.addActionListener(e -> {
            textField1.setText(""+cnt++);
            label2.setText("bbb");
        });
    }
    //https://github.com/chennaione/sugar
    public static void main(String[] args){
        JFrame frame = new BankGUI("bank");
        frame.setVisible(true);
    }
}
