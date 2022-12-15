import Utils.Logger;
import bankUI.BankGUI;
import bankBackend.BankBackend;
import bankUI.components.home.HomeUI;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        BankBackend bnk = new BankBackend();
        Logger.info("Starting bank " + bnk.name);
        HomeUI.main(args);
        //BankGUI.initGUI(args);
        //HomeGUI.main(args);
    }
}
