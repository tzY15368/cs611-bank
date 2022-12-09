package bankBackend.entity.account;

import bankBackend.entity.enums.AccountType;

public class LoanAccount extends Account {
    public LoanAccount() {
        // ORMLite needs a no-arg constructor
        super(-1, AccountType.Loan);
    }

    // IMPORTANT:
    // the semantics are different here,
    // the actual balances are the amount of money you OWE
    public LoanAccount(int userId) {
        super(userId, AccountType.Loan);
    }

}
