import Utils.Logger;
import bankBackend.BankBackend;
import bankUI.components.login.Authentication;
import bankUI.utils.SystemMgr;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        BankBackend bnk = new BankBackend();
        Logger.info("Starting bank " + bnk.name);
        //BankGUI.initGUI(args);
        //HomeGUI.main(args);
        SystemMgr.main(args);
        Authentication.main(args);
        //DeprecatedHomeGUI.main(args);
    }
}
