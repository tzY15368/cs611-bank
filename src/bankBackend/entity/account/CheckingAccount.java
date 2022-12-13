package bankBackend.entity.account;

import bankBackend.entity.enums.AccountState;
import bankBackend.entity.enums.AccountType;

public class CheckingAccount extends Account {
    public CheckingAccount() {
        // ORMLite needs a no-arg constructor
        super(-1, AccountType.CHECKING);
    }

    public CheckingAccount(int userId) {
        super(userId, AccountType.CHECKING);
        this.state = AccountState.OPEN;
    }

}

