package bankUI.components.home;

import Utils.Logger;
import Utils.Result;
import bankBackend.entity.User;
import bankBackend.entity.account.Account;
import bankBackend.service.SvcMgr;
import bankBackend.entity.Balance;
import bankBackend.entity.enums.CurrencyType;
import bankUI.components.account.AccountStateUI;
import bankUI.components.account.MoneyIOUI;
import bankUI.components.account.TxnBuilderUI;
import bankUI.components.interest.LoanUI;
import bankUI.components.interest.SavingUI;
import bankUI.components.login.Authentication;
import bankUI.components.stock.StockNewUI;
import bankUI.utils.AlertUI;
import bankUI.utils.UIContextMgr;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

public class HomeUI3 extends javax.swing.JFrame {

    /**
     * Creates new form homeUI
     */
    private User user;


    public HomeUI3(User user) {
        this.user = user;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        UserLabel = new javax.swing.JLabel();
        WelcomeLabel = new javax.swing.JLabel();
        CheckingPanel = new javax.swing.JPanel();
        CheckingLabel = new javax.swing.JLabel();
        CheckingUSD = new javax.swing.JLabel();
        CheckingEUR = new javax.swing.JLabel();
        CheckingGBP = new javax.swing.JLabel();
        CheckingUSDValue = new javax.swing.JLabel();
        CheckingEURValue = new javax.swing.JLabel();
        CheckingGBPValue = new javax.swing.JLabel();
        CheckingState = new javax.swing.JLabel();
        SavingPanel = new javax.swing.JPanel();
        SavingLabel = new javax.swing.JLabel();
        SavingUSDLabel = new javax.swing.JLabel();
        SavingEURLabel = new javax.swing.JLabel();
        SavingGBPLabel = new javax.swing.JLabel();
        SavingUSDValue = new javax.swing.JLabel();
        SavingEURValue = new javax.swing.JLabel();
        SavingGBPValue = new javax.swing.JLabel();
        SavingState = new javax.swing.JLabel();
        savingBtn = new javax.swing.JButton();
        SecurityPanel = new javax.swing.JPanel();
        SecurityLabel = new javax.swing.JLabel();
        SecurityUSDLabel = new javax.swing.JLabel();
        SecurityEURLabel = new javax.swing.JLabel();
        SecurityGBPLabel = new javax.swing.JLabel();
        SecurityUSDValue = new javax.swing.JLabel();
        SecurityEURValue = new javax.swing.JLabel();
        SecurityGBPValue = new javax.swing.JLabel();
        SecurityState = new javax.swing.JLabel();
        stockBtn = new javax.swing.JButton();
        LoanPanel = new javax.swing.JPanel();
        LoanLabel = new javax.swing.JLabel();
        LoanUSDLabel = new javax.swing.JLabel();
        LoanEURLabel = new javax.swing.JLabel();
        LoanGBPLabel = new javax.swing.JLabel();
        LoanUSDValue = new javax.swing.JLabel();
        LoanEURValue = new javax.swing.JLabel();
        LoanGBPValue = new javax.swing.JLabel();
        LoanState = new javax.swing.JLabel();
        loanBtn = new javax.swing.JButton();
        refreshBtn = new javax.swing.JButton();
        logoutBtn = new javax.swing.JButton();
        moneyIOButton = new javax.swing.JButton();
        moneyIOButton1 = new javax.swing.JButton();
        reportBtn = new javax.swing.JButton();
        txBtn = new javax.swing.JButton();
        stateBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        UserLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        UserLabel.setText(this.user==null?"":this.user.getName());

        WelcomeLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        WelcomeLabel.setText("Welcome, ");

        CheckingLabel.setText("Checking Account");

        CheckingUSD.setText("USD");

        CheckingEUR.setText("EUR");

        CheckingGBP.setText("GBP");

        CheckingUSDValue.setText(""+Balance.getBalanceWithCurrency(user.getCheckingAccount().data.getId(),CurrencyType.USD).unwrap().getValue());

        CheckingEURValue.setText(""+Balance.getBalanceWithCurrency(user.getCheckingAccount().data.getId(),CurrencyType.EUR).unwrap().getValue());

        CheckingGBPValue.setText(""+Balance.getBalanceWithCurrency(user.getCheckingAccount().data.getId(),CurrencyType.GBP).unwrap().getValue());

        CheckingState.setText(user.getCheckingAccount().data.getState().toString());

        javax.swing.GroupLayout CheckingPanelLayout = new javax.swing.GroupLayout(CheckingPanel);
        CheckingPanel.setLayout(CheckingPanelLayout);
        CheckingPanelLayout.setHorizontalGroup(
            CheckingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CheckingPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(CheckingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CheckingLabel)
                    .addGroup(CheckingPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(CheckingState)))
                .addGap(89, 89, 89)
                .addGroup(CheckingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CheckingPanelLayout.createSequentialGroup()
                        .addComponent(CheckingUSD)
                        .addGap(73, 73, 73)
                        .addComponent(CheckingUSDValue))
                    .addGroup(CheckingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(CheckingPanelLayout.createSequentialGroup()
                            .addComponent(CheckingEUR)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CheckingEURValue))
                        .addGroup(CheckingPanelLayout.createSequentialGroup()
                            .addComponent(CheckingGBP)
                            .addGap(73, 73, 73)
                            .addComponent(CheckingGBPValue))))
                .addGap(196, 196, 196))
        );
        CheckingPanelLayout.setVerticalGroup(
            CheckingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CheckingPanelLayout.createSequentialGroup()
                .addGroup(CheckingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CheckingPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(CheckingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CheckingUSD)
                            .addComponent(CheckingUSDValue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CheckingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CheckingEUR)
                            .addComponent(CheckingEURValue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CheckingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CheckingGBP)
                            .addComponent(CheckingGBPValue)))
                    .addGroup(CheckingPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(CheckingLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CheckingState)))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        SavingLabel.setText("Saving Account");

        SavingUSDLabel.setText("USD");

        SavingEURLabel.setText("EUR");

        SavingGBPLabel.setText("GBP");

        SavingUSDValue.setText(""+Balance.getBalanceWithCurrency(user.getSavingAccount().data.getId(),CurrencyType.USD).unwrap().getValue());

        SavingEURValue.setText(""+Balance.getBalanceWithCurrency(user.getSavingAccount().data.getId(),CurrencyType.EUR).unwrap().getValue());

        SavingGBPValue.setText(""+Balance.getBalanceWithCurrency(user.getSavingAccount().data.getId(),CurrencyType.GBP).unwrap().getValue());

        SavingState.setText(user.getSavingAccount().data.getState().toString());

        savingBtn.setText("goto saving");
        savingBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                savingBtnMousePressed(evt);
            }
        });

        javax.swing.GroupLayout SavingPanelLayout = new javax.swing.GroupLayout(SavingPanel);
        SavingPanel.setLayout(SavingPanelLayout);
        SavingPanelLayout.setHorizontalGroup(
            SavingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SavingPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(SavingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SavingLabel)
                    .addComponent(SavingState))
                .addGap(104, 104, 104)
                .addGroup(SavingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SavingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(SavingUSDLabel)
                        .addComponent(SavingEURLabel))
                    .addComponent(SavingGBPLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(71, 71, 71)
                .addGroup(SavingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SavingUSDValue)
                    .addComponent(SavingEURValue)
                    .addComponent(SavingGBPValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(savingBtn)
                .addGap(45, 45, 45))
        );
        SavingPanelLayout.setVerticalGroup(
            SavingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SavingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SavingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SavingUSDLabel)
                    .addComponent(SavingUSDValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SavingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SavingEURLabel)
                    .addComponent(SavingEURValue)
                    .addComponent(savingBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SavingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SavingPanelLayout.createSequentialGroup()
                        .addGap(0, 6, Short.MAX_VALUE)
                        .addComponent(SavingGBPLabel))
                    .addGroup(SavingPanelLayout.createSequentialGroup()
                        .addComponent(SavingGBPValue)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(SavingPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(SavingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SavingState)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SecurityLabel.setText("Secutity Account");

        SecurityUSDLabel.setText("USD");

        SecurityEURLabel.setText("EUR");

        SecurityGBPLabel.setText("GBP");

        SecurityUSDValue.setText(""+Balance.getBalanceWithCurrency(user.getSecurityAccount().data.getId(),CurrencyType.USD).unwrap().getValue());

        SecurityEURValue.setText(""+Balance.getBalanceWithCurrency(user.getSecurityAccount().data.getId(),CurrencyType.EUR).unwrap().getValue());

        SecurityGBPValue.setText(""+Balance.getBalanceWithCurrency(user.getSecurityAccount().data.getId(),CurrencyType.GBP).unwrap().getValue());

        SecurityState.setText(user.getSecurityAccount().data.getState().toString());

        stockBtn.setText("goto stock");
        stockBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SecurityPanelLayout = new javax.swing.GroupLayout(SecurityPanel);
        SecurityPanel.setLayout(SecurityPanelLayout);
        SecurityPanelLayout.setHorizontalGroup(
            SecurityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SecurityPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(SecurityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SecurityLabel)
                    .addGroup(SecurityPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(SecurityState)))
                .addGap(101, 101, 101)
                .addGroup(SecurityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(SecurityPanelLayout.createSequentialGroup()
                        .addComponent(SecurityGBPLabel)
                        .addGap(72, 72, 72)
                        .addComponent(SecurityGBPValue))
                    .addGroup(SecurityPanelLayout.createSequentialGroup()
                        .addComponent(SecurityEURLabel)
                        .addGap(73, 73, 73)
                        .addComponent(SecurityEURValue))
                    .addGroup(SecurityPanelLayout.createSequentialGroup()
                        .addComponent(SecurityUSDLabel)
                        .addGap(73, 73, 73)
                        .addComponent(SecurityUSDValue)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(stockBtn)
                .addGap(43, 43, 43))
        );
        SecurityPanelLayout.setVerticalGroup(
            SecurityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SecurityPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SecurityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SecurityUSDLabel)
                    .addComponent(SecurityUSDValue))
                .addGroup(SecurityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SecurityPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(SecurityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SecurityEURLabel)
                            .addComponent(SecurityEURValue)
                            .addComponent(stockBtn)))
                    .addGroup(SecurityPanelLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(SecurityLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SecurityState)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(SecurityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SecurityGBPLabel)
                    .addComponent(SecurityGBPValue)))
        );

        LoanLabel.setText("Loan Account");

        LoanUSDLabel.setText("USD");

        LoanEURLabel.setText("EUR");

        LoanGBPLabel.setText("GBP");

        LoanUSDValue.setText(""+Balance.getBalanceWithCurrency(user.getLoanAccount().data.getId(),CurrencyType.USD).unwrap().getValue());

        LoanEURValue.setText(""+Balance.getBalanceWithCurrency(user.getLoanAccount().data.getId(),CurrencyType.EUR).unwrap().getValue());

        LoanGBPValue.setText(""+Balance.getBalanceWithCurrency(user.getLoanAccount().data.getId(),CurrencyType.GBP).unwrap().getValue());

        LoanState.setText(user.getLoanAccount().data.getState().toString());

        loanBtn.setText("goto loan");
        loanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loanBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LoanPanelLayout = new javax.swing.GroupLayout(LoanPanel);
        LoanPanel.setLayout(LoanPanelLayout);
        LoanPanelLayout.setHorizontalGroup(
            LoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoanPanelLayout.createSequentialGroup()
                .addGroup(LoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LoanPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(LoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LoanLabel)
                            .addGroup(LoanPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(LoanState)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(LoanGBPLabel))))
                    .addGroup(LoanPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(LoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LoanEURLabel)
                            .addComponent(LoanUSDLabel))))
                .addGap(73, 73, 73)
                .addGroup(LoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LoanUSDValue)
                    .addComponent(LoanEURValue)
                    .addComponent(LoanGBPValue))
                .addGap(63, 63, 63)
                .addComponent(loanBtn)
                .addGap(44, 44, 44))
        );
        LoanPanelLayout.setVerticalGroup(
            LoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoanPanelLayout.createSequentialGroup()
                .addGroup(LoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LoanPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(LoanLabel))
                    .addGroup(LoanPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(LoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LoanUSDLabel)
                            .addComponent(LoanUSDValue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(LoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LoanEURLabel)
                            .addComponent(LoanEURValue)
                            .addComponent(loanBtn))))
                .addGroup(LoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LoanPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(LoanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LoanGBPValue)
                            .addComponent(LoanGBPLabel))
                        .addContainerGap())
                    .addGroup(LoanPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LoanState)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        refreshBtn.setText("refresh");
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });

        logoutBtn.setText("logout");
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(LoanPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SecurityPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CheckingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SavingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(31, 31, 31)
                        .addComponent(logoutBtn))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(WelcomeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UserLabel)
                        .addGap(202, 202, 202)
                        .addComponent(refreshBtn)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UserLabel)
                    .addComponent(WelcomeLabel)
                    .addComponent(refreshBtn))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CheckingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(logoutBtn)))
                .addGap(18, 18, 18)
                .addComponent(SavingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SecurityPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LoanPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        moneyIOButton.setText("Deposit/Withdraw");
        moneyIOButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moneyIOButtonActionPerformed(evt);
            }
        });

        moneyIOButton1.setText("Deposit/Withdraw");
        moneyIOButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moneyIOButton1ActionPerformed(evt);
            }
        });

        reportBtn.setText("View Report");
        reportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportBtnActionPerformed(evt);
            }
        });

        txBtn.setText("goto txbuilder");
        txBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txBtnActionPerformed(evt);
            }
        });

        stateBtn.setText("goto state");
        stateBtn.setActionCommand("");
        stateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(moneyIOButton)
                .addGap(58, 58, 58)
                .addComponent(reportBtn)
                .addGap(90, 90, 90)
                .addComponent(txBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(stateBtn)
                .addGap(61, 61, 61))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(329, 329, 329)
                    .addComponent(moneyIOButton1)
                    .addContainerGap(330, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moneyIOButton)
                    .addComponent(reportBtn)
                    .addComponent(txBtn)
                    .addComponent(stateBtn))
                .addGap(0, 11, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(238, 238, 238)
                    .addComponent(moneyIOButton1)
                    .addContainerGap(238, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void moneyIOButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moneyIOButtonActionPerformed
        // TODO add your handling code here:
        MoneyIOUI money = new MoneyIOUI();
        money.setVisible(true);
    }//GEN-LAST:event_moneyIOButtonActionPerformed

    private void moneyIOButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moneyIOButton1ActionPerformed
        // TODO add your handling code here:
        MoneyIOUI money = new MoneyIOUI();
        money.setVisible(true);
    }//GEN-LAST:event_moneyIOButton1ActionPerformed

    private void reportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportBtnActionPerformed
        // TODO add your handling code here:
        TxnReport report = new TxnReport();
        report.setVisible(true);
    }//GEN-LAST:event_reportBtnActionPerformed

    private void loanBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loanBtnActionPerformed
        // TODO add your handling code here:
        LoanUI loan = new LoanUI();
        UIContextMgr.setAccount(loan, SvcMgr.getSessionService().getSession().unwrap().getUser().getLoanAccount().unwrap());
        loan.loadCtx();
        loan.setVisible(true);
    }//GEN-LAST:event_loanBtnActionPerformed

    private void stockBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockBtnActionPerformed
        // TODO add your handling code here:
        StockNewUI stock = new StockNewUI();
        UIContextMgr.setUser(stock, SvcMgr.getSessionService().getSession().unwrap().getUser());
        stock.setVisible(true);
    }//GEN-LAST:event_stockBtnActionPerformed

    private void savingBtnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_savingBtnMousePressed
        // TODO add your handling code here:
        SavingUI saving = new SavingUI();
        UIContextMgr.setAccount(saving, SvcMgr.getSessionService().getSession().unwrap().getUser().getLoanAccount().unwrap());
        saving.loadCtx();
        saving.setVisible(true);
    }//GEN-LAST:event_savingBtnMousePressed

    private void txBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txBtnActionPerformed
        // TODO add your handling code here:
        TxnBuilderUI txn = new TxnBuilderUI();
        UIContextMgr.setUser(txn, SvcMgr.getSessionService().getSession().unwrap().getUser());
        txn.setVisible(true);
    }//GEN-LAST:event_txBtnActionPerformed

    private void stateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateBtnActionPerformed
        // TODO add your handling code here:
        AccountStateUI state = new AccountStateUI();
        UIContextMgr.setUser(state, SvcMgr.getSessionService().getSession().unwrap().getUser());
        state.setVisible(true);
    }//GEN-LAST:event_stateBtnActionPerformed

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        // TODO add your handling code here:

        initComponents();
        Logger.info("re-init components");
    }//GEN-LAST:event_refreshBtnActionPerformed

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed
        // TODO add your handling code here:
        Authentication aut = new Authentication();
        aut.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_logoutBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CheckingEUR;
    private javax.swing.JLabel CheckingEURValue;
    private javax.swing.JLabel CheckingGBP;
    private javax.swing.JLabel CheckingGBPValue;
    private javax.swing.JLabel CheckingLabel;
    private javax.swing.JPanel CheckingPanel;
    private javax.swing.JLabel CheckingState;
    private javax.swing.JLabel CheckingUSD;
    private javax.swing.JLabel CheckingUSDValue;
    private javax.swing.JLabel LoanEURLabel;
    private javax.swing.JLabel LoanEURValue;
    private javax.swing.JLabel LoanGBPLabel;
    private javax.swing.JLabel LoanGBPValue;
    private javax.swing.JLabel LoanLabel;
    private javax.swing.JPanel LoanPanel;
    private javax.swing.JLabel LoanState;
    private javax.swing.JLabel LoanUSDLabel;
    private javax.swing.JLabel LoanUSDValue;
    private javax.swing.JLabel SavingEURLabel;
    private javax.swing.JLabel SavingEURValue;
    private javax.swing.JLabel SavingGBPLabel;
    private javax.swing.JLabel SavingGBPValue;
    private javax.swing.JLabel SavingLabel;
    private javax.swing.JPanel SavingPanel;
    private javax.swing.JLabel SavingState;
    private javax.swing.JLabel SavingUSDLabel;
    private javax.swing.JLabel SavingUSDValue;
    private javax.swing.JLabel SecurityEURLabel;
    private javax.swing.JLabel SecurityEURValue;
    private javax.swing.JLabel SecurityGBPLabel;
    private javax.swing.JLabel SecurityGBPValue;
    private javax.swing.JLabel SecurityLabel;
    private javax.swing.JPanel SecurityPanel;
    private javax.swing.JLabel SecurityState;
    private javax.swing.JLabel SecurityUSDLabel;
    private javax.swing.JLabel SecurityUSDValue;
    private javax.swing.JLabel UserLabel;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton loanBtn;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JButton moneyIOButton;
    private javax.swing.JButton moneyIOButton1;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JButton reportBtn;
    private javax.swing.JButton savingBtn;
    private javax.swing.JButton stateBtn;
    private javax.swing.JButton stockBtn;
    private javax.swing.JButton txBtn;
    // End of variables declaration//GEN-END:variables
}
