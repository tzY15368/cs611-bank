import bankUI.BankGUI;
import bankBackend.BankBackend;

public class Main {
    public static void main(String[] args){
        BankBackend bnk = new BankBackend();
        System.out.println(bnk.name);
        BankGUI.initGUI(args);
    }
}
