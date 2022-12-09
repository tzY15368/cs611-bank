import Utils.Logger;
import bankUI.BankGUI;
import bankBackend.BankBackend;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        BankBackend bnk = new BankBackend();
        Logger.info("Starting bank " + bnk.name);
        //BankGUI.initGUI(args);
        HomeGUI.main(args);
    }
}
