package bankUI.AccountPanel;


import bankBackend.entity.User;
import bankBackend.service.SvcMgr;


import javax.swing.*;
import java.awt.*;

public class AccountPanel extends JPanel {
    private JLabel jLabel;
    private DefaultListModel ListModel;
    private JButton jButton;
    //User user = SessionMgr.getSession().data.getUser();

    public AccountPanel() {
        this.jButton = new JButton();
        this.jLabel = new JLabel();
        this.ListModel = new DefaultListModel();
    }

    public void iniAccountPanel() {

    }

    public JLabel getjLabel() {
        return jLabel;
    }

    public void setjLabel(JLabel jLabel) {
        this.jLabel = jLabel;
    }

    public JButton getjButton() {
        return jButton;
    }

    public void setjButton(JButton jButton) {
        this.jButton = jButton;
    }

    public DefaultListModel getListModel() {
        return ListModel;
    }

    public void setListModel(DefaultListModel listModel) {
        ListModel = listModel;
    }

    public void inijLabel() {
    }

    public void inijButton() {
    }

    public void iniListModel() {
    }


}
