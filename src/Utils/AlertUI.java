package Utils;

import javax.swing.*;
import java.awt.*;

public class AlertUI {
    public static void success(String msg){
        popup(msg, "Success");
    }
    public static void error(String msg){
        popup(msg,"Error");
    }

    private static void popup(String msg, String title){
        JDialog jDialog=new JDialog();
        jDialog.setSize(200,80);
        jDialog.setLocationRelativeTo(null);
        jDialog.setTitle(title);
        jDialog.setVisible(true);
        Container contentPane= jDialog.getContentPane();
        contentPane.add(new JLabel(msg));

    }

}
