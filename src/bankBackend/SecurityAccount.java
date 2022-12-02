package bankBackend;

public class SecurityAccount extends Account {
    public SecurityAccount() {
        // ORMLite needs a no-arg constructor
        super(-1, AccountType.Security);
    }

    public SecurityAccount(int userId) {
        super(userId, AccountType.Security);
    }

}
