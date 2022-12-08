import Utils.Logger;
import Utils.Timer;
import bankUI.BankGUI;
import bankBackend.BankBackend;
import bankUI.HomeGUI;

public class Main {
    public static void main(String[] args) {
        BankBackend bnk = new BankBackend();
        Logger.info("Starting bank " + bnk.name);
        BankGUI.initGUI(args);
        //HomeGUI.main(args);
    }
}
