package bankBackend.entity.account;

import bankBackend.entity.enums.AccountType;

public class LoanAccount extends Account {
    public LoanAccount() {
        // ORMLite needs a no-arg constructor
        super(-1, AccountType.Loan);
    }

    public LoanAccount(int userId) {
        super(userId, AccountType.Loan);
    }

}
