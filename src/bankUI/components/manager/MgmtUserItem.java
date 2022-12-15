/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package bankUI.components.manager;

import Utils.Result;
import bankBackend.entity.Balance;
import bankBackend.entity.User;
import bankBackend.entity.enums.CurrencyType;
import bankBackend.service.SvcMgr;
import bankUI.components.home.HomeUI3;
import bankUI.utils.AlertUI;
import bankUI.utils.UIContextMgr;

/**
 * @author NathanY
 */
public class MgmtUserItem extends javax.swing.JPanel {

    /**
     * Creates new form MgmtUserItem
     */
    private String upass = "";
    private String uname = "";
    private int ubalance = 0;
    private User user;

    public MgmtUserItem() {
        initComponents();
    }

    public void loadCtx() {
        User u = UIContextMgr.getUser(this);
        this.user = user;
        if (u != null) {
            uname = u.getName();
            upass = u.getPassword();
            ubalance = Balance.getBalanceWithCurrency(u.getCheckingAccount()
                    .unwrap().getId(), CurrencyType.USD).unwrap().getValue();
            unameF.setText(uname);
            upassF.setText(upass);
            balanceF.setText("" + ubalance);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        upassF = new javax.swing.JTextField();
        saveBtn = new javax.swing.JButton();
        unameF = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        balanceF = new javax.swing.JLabel();
        detailsBtn = new javax.swing.JButton();

        jLabel1.setText("username");

        jLabel3.setText("password");

        upassF.setText(this.upass);

        saveBtn.setText("save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        unameF.setText(uname);

        jLabel2.setText("Saving account USD balance");

        balanceF.setText("" + ubalance);

        detailsBtn.setText("goto details");
        detailsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailsBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(balanceF, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(unameF, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(upassF, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(2, 2, 2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(detailsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(saveBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel3)
                                        .addComponent(upassF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(saveBtn)
                                        .addComponent(unameF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(balanceF)
                                        .addComponent(detailsBtn))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        // TODO add your handling code here:
        if (this.unameF.getText().equals("") || this.upassF.getText().equals("")) {
            AlertUI.error("Please fill all fields");
        } else {
            Result r = SvcMgr.getUserService().updateUser(this.user.getId(), this.unameF.getText(), this.upassF.getText());
            if (r.success) {
                AlertUI.success("User updated successfully");
                this.uname = this.unameF.getText();
                this.upass = this.upassF.getText();
            } else {
                AlertUI.error(r.msg);
                return;
            }
        }
    }//GEN-LAST:event_saveBtnActionPerformed

    private void detailsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailsBtnActionPerformed
        // TODO add your handling code here:
        HomeUI3 home = new HomeUI3(this.user);
        home.setVisible(true);
    }//GEN-LAST:event_detailsBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel balanceF;
    private javax.swing.JButton detailsBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton saveBtn;
    private javax.swing.JTextField unameF;
    private javax.swing.JTextField upassF;
    // End of variables declaration//GEN-END:variables
}