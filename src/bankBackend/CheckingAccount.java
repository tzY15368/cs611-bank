package bankBackend;

public class CheckingAccount extends Account {
    public CheckingAccount() {
        // ORMLite needs a no-arg constructor
        super(-1, AccountType.CHECKING);
    }

    public CheckingAccount(int userId) {
        super(userId, AccountType.CHECKING);
    }

}

