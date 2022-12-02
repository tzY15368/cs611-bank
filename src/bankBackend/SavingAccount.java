package bankBackend;

public class SavingAccount extends Account {

    public SavingAccount() {
        // ORMLite needs a no-arg constructor
        super(-1, AccountType.SAVINGS);
    }

    public SavingAccount(int userId) {
        super(userId, AccountType.SAVINGS);
    }

}
