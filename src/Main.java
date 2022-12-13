import Utils.Logger;
import bankUI.BankGUI;
import bankBackend.BankBackend;
import bankUI.components.login.Authentication;

public class Main {
    public static void main(String[] args) {
        BankBackend bnk = new BankBackend();
        Logger.info("Starting bank " + bnk.name);
        //BankGUI.initGUI(args);
        //HomeGUI.main(args);
        Authentication.main(args);
    }
}
