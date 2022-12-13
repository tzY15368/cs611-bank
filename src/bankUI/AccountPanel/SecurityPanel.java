package bankUI.AccountPanel;

import Utils.Logger;
import Utils.Result;
import bankBackend.SecurityAccount;

import javax.swing.*;
import java.awt.*;

public class SecurityPanel extends AccountPanel {

    public void iniSecurityPanel(){
        JLabel jLabel=getjLabel();
        jLabel.setText("Security Account");
        this.add(jLabel);

        JButton jButton=getjButton();
        jButton.setText("Enter");
        this.add(jButton);


        jButton.addActionListener(e -> {
            Logger.info("security account button clicked");

                // TODO:jump to security account page

        });

    }
}
