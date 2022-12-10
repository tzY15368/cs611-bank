package bankUI.AccountPanel;

import Utils.Logger;
import Utils.Result;
import bankBackend.entity.account.LoanAccount;

import javax.swing.*;

public class LoanPanel extends AccountPanel {
    public LoanPanel(){iniLoanPanel();}

    public void iniLoanPanel(){

        JLabel jLabel=getjLabel();
        jLabel.setText("Loan Account");
        this.add(jLabel);

        JButton jButton=getjButton();
        jButton.setText("Enter");
        this.add(jButton);


        jButton.addActionListener(e -> {
            Logger.info("loan account button clicked");

                // TODO:jump to loan account page

        });

    }
}
