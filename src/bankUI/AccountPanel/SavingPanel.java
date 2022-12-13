package bankUI.AccountPanel;

import Utils.Logger;
import Utils.Result;
import bankBackend.entity.Balance;
import bankBackend.entity.account.Account;
import bankBackend.service.SvcMgr;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SavingPanel extends AccountPanel {

    public SavingPanel() {
        iniSavingPanel();
    }

    public void iniSavingPanel() {
        JLabel jLabel = getjLabel();
        jLabel.setText("Saving Account");
        this.add(jLabel);

        DefaultListModel ListModel = getListModel();

        Result<Account> res = user.getSavingAccount();
        if (res.success) {
            List<Balance> savingBalanceList = SvcMgr.getAccountService().listBalance(res.unwrap().getId());
            for (Balance balance : savingBalanceList
            ) {
                ListModel.addElement(balance.getType() + ":   " + balance.getValue());
            }
        } else {
            Logger.warn(res.msg);
        }
        ListModel.addElement("Balance Type:   " + "   Value");
        ListModel.addElement("Balance Type:   " + "   Value");
        JList savingBalanceJList = new JList<>(ListModel);
        savingBalanceJList.setBounds(200, 150, 50, 100);
        savingBalanceJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        savingBalanceJList.setSelectedIndex(0);
        savingBalanceJList.setVisibleRowCount(5);
        this.add(savingBalanceJList);


        JButton jButton = getjButton();
        jButton.setText("Enter");
        this.add(jButton);

        //TODO:
        jButton.addActionListener(e -> {
            Logger.info("saving account button clicked");
            Result<Account> result = user.getSavingAccount();
            if (!result.success) {
                Logger.warn(result.msg);
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

    }
}
