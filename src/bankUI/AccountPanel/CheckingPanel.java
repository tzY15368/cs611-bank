package bankUI.AccountPanel;

import Utils.Logger;
import Utils.Result;
//import Utils.SessionMgr;
import bankBackend.entity.Balance;
import bankBackend.entity.account.CheckingAccount;
import bankBackend.entity.User;
import java.util.List;

import javax.swing.*;
import java.awt.*;

public class CheckingPanel extends AccountPanel {
/*
    public CheckingPanel(){
        iniCheckingPanel();
    }


    public void iniCheckingPanel(){

        JLabel jLabel = getjLabel();
        jLabel.setText("Checking Account");
        jLabel.setBounds(10,10,20,20);
        jLabel.setFont(new Font("Arial",Font.PLAIN, 40));
        jLabel.getPreferredSize();
        this.add(jLabel);

        DefaultListModel ListModel=getListModel();

        Result res=user.getCheckingAccount();
        if(res.success) {
            List<Balance> checkingBalanceList = user.getCheckingAccount().getData().listBalance();
            for (Balance balance : checkingBalanceList
            ) {
                ListModel.addElement(balance.getType() + ":   " + balance.getValue());
            }
        }
        else{
            Logger.warn(res.msg);
        }


        ListModel.addElement("Balance Type:   "+"   Value");
        ListModel.addElement("Balance Type:   "+"   Value");
        JList balanceJList=new JList<>(ListModel);
        balanceJList.setBounds(200,150,50,100);
        balanceJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        balanceJList.setSelectedIndex(0);
        balanceJList.setVisibleRowCount(5);
        this.add(balanceJList);

        JButton jButton=getjButton();
        jButton.setText("Enter");
        jButton.setBounds(400,150,50,20);
        jButton.setVisible(true);
        this.add(jButton);


        jButton.addActionListener(e -> {
            Logger.info("checking account button clicked");
            Result<CheckingAccount> result=user.getCheckingAccount();
            if (!result.success) {
                Logger.warn(result.msg);
                JDialog jDialog=new JDialog();
                jDialog.setSize(200,80);
                jDialog.setLocationRelativeTo(null);
                jDialog.setTitle("Hint");
                jDialog.setVisible(true);
                Container contentPane= jDialog.getContentPane();
                contentPane.add(new JLabel("You don't have checking account!"));
            }
            else{
                // TODO:jump to checking account page
            }
        });




    }

 */



}
