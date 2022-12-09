
package bankUI;

import Utils.Logger;
import bankBackend.service.impl.SessionCtl;
import bankBackend.entity.User;

import javax.swing.*;
import java.awt.*;


public class HomeGUI extends JPanel {
    private int userID;
    private JFrame jFrame;
    private JPanel checkingPanel;
    private JPanel savingPanel;
    private JPanel securityPanel;
    private JPanel loanPanel;
    private JButton checkingButton;
    private JButton savingButton;
    private JButton securityButton;
    private JButton loanButton;

    private DefaultListModel checkingListModel;
    private DefaultListModel savingListModel;
    private DefaultListModel securityListModel;
    private JCheckBox checkBox1;

    public HomeGUI(int userID) {
        // TODO: User user=User.findUserById(int userID);
        User user = SessionCtl.getSession().data.getUser();
        JLabel title = new JLabel("User Name： " + user.getName());
        title.setBounds(350, 20, 300, 100);
        title.setFont(new Font("Arial", Font.PLAIN, 30));

        // checking account panel


        JLabel jLabel2 = new JLabel("Checking Account");

        jLabel2.setBounds(110, 110, 20, 20);
        jLabel2.setFont(new Font("Arial", Font.PLAIN, 40));
        jLabel2.getPreferredSize();

        checkingPanel = new JPanel();
        checkingPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        checkingPanel.setBounds(100, 100, 800, 100);
        checkingPanel.add(jLabel2);

        checkingListModel = new DefaultListModel();

        /*
        User user= new User();
        Result res=user.getCheckingAccount();
        if(!res.success){

        }
        List<Balance> checkingBalanceList=user.getCheckingAccount().getData().listBalance();
        for (Balance balance:checkingBalanceList
             ) {
            checkingListModel.addElement(balance.getType()+":   "+balance.getValue());
        }

         */


        checkingListModel.addElement("Balance Type:   " + "   Value");
        checkingListModel.addElement("Balance Type:   " + "   Value");
        JList balanceJList = new JList<>(checkingListModel);
        balanceJList.setBounds(200, 150, 50, 100);
        balanceJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        balanceJList.setSelectedIndex(0);
        balanceJList.setVisibleRowCount(5);
        checkingPanel.add(balanceJList);

        checkingButton = new JButton("Enter");
        checkingButton.setBounds(400, 150, 50, 20);
        checkingButton.setVisible(true);
        checkingPanel.add(checkingButton);

        /*
        TODO:
        checkingButton.addActionListener(e -> {
            Logger.info("checking account button clicked");
            Result<CheckingAccount> result=user.getCheckingAccount();
            if (!result.success) {
                Logger.warn(result.msg);
            }
            else{
                // TODO:jump to checking account page
            }
        });
         */

        // saving account panel
        savingPanel = new JPanel();
        savingPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        savingPanel.setBounds(100, 210, 800, 100);
        JLabel jLabel3 = new JLabel("Saving Account");
        savingPanel.add(jLabel3);

        savingListModel = new DefaultListModel();

        /*
        List<Balance> savingBalanceList=user.getSavingAccount().getData().listBalance();
        for (Balance balance:savingBalanceList
             ) {
            savingListModel.addElement(balance.getType()+":   "+balance.getValue());
        }
         */

        savingListModel.addElement("Balance Type:   " + "   Value");
        savingListModel.addElement("Balance Type:   " + "   Value");
        JList savingBalanceJList = new JList<>(savingListModel);
        savingBalanceJList.setBounds(200, 150, 50, 100);
        savingBalanceJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        savingBalanceJList.setSelectedIndex(0);
        savingBalanceJList.setVisibleRowCount(5);
        savingPanel.add(savingBalanceJList);

        savingButton = new JButton("Enter");
        savingPanel.add(savingButton);


        //TODO:
        savingButton.addActionListener(e -> {
            Logger.info("saving account button clicked");
            //Result<SavingAccount> result=user.getSavingAccount();
            if (true) {//!result.success
                //Logger.warn(result.msg);
                JDialog jDialog = new JDialog();
                jDialog.setSize(200, 80);
                jDialog.setLocationRelativeTo(null);
                jDialog.setTitle("Hint");
                jDialog.setVisible(true);
                Container contentPane = jDialog.getContentPane();
                contentPane.add(new JLabel("You don't have saving account!"));
            } else {
                // TODO:jump to saving account page
            }
        });


        //security account panel
        securityPanel = new JPanel();
        securityPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        securityPanel.setBounds(100, 320, 800, 100);
        JLabel jLabel4 = new JLabel("Security Account");
        securityPanel.add(jLabel4);

        securityButton = new JButton("Enter");
        securityPanel.add(securityButton);

         /*
        TODO:
        securityButton.addActionListener(e -> {
            Logger.info("security account button clicked");
            Result<SecurityAccount> result=user.getSecurityAccount();
            if (!result.success) {
                Logger.warn(result.msg);
            }
            else{
                // TODO:jump to security account page
            }
        });
         */

        //loan account panel
        loanPanel = new JPanel();
        loanPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        loanPanel.setBounds(100, 430, 800, 100);
        JLabel jLabel5 = new JLabel("Loan Account");
        loanPanel.add(jLabel5);

        loanButton = new JButton("Enter");
        loanPanel.add(loanButton);

        /*
        TODO:
        loanButton.addActionListener(e -> {
            Logger.info("loan account button clicked");
            Result<LoanAccount> result=user.getLoanAccount();
            if (!result.success) {
                Logger.warn(result.msg);
            }
            else{
                // TODO:jump to loan account page
            }
        });
         */

        // frame
        jFrame = new JFrame("Home");
        jFrame.setSize(1000, 700);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(null);
        jFrame.getContentPane().add(title);
        jFrame.getContentPane().add(checkingPanel);
        jFrame.getContentPane().add(savingPanel);
        jFrame.getContentPane().add(securityPanel);
        jFrame.getContentPane().add(loanPanel);
        jFrame.setVisible(true);

    }

    //test page
    public static void main(String[] args) {
        new HomeGUI(1234);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }


}
