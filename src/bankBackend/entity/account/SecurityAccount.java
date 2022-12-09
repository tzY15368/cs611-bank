package bankBackend.entity.account;


import bankBackend.entity.enums.AccountType;

public class SecurityAccount extends Account {

    public SecurityAccount() {
        // ORMLite needs a no-arg constructor
        super(-1, AccountType.Security);
    }

    public SecurityAccount(int userId) {
        super(userId, AccountType.Security);
    }

}
