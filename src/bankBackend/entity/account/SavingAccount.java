package bankBackend.entity.account;

import Utils.Logger;
import bankBackend.entity.enums.AccountType;

public class SavingAccount extends Account {

    public SavingAccount() {
        // ORMLite needs a no-arg constructor
        super(-1, AccountType.SAVINGS);
    }

    public SavingAccount(int userId) {
        super(userId, AccountType.SAVINGS);
    }

    public static void generateInterestCallback(int date, int hour) {
        // TODO
        Logger.info("Generating interest...");
    }
}
