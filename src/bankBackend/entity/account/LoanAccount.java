package bankBackend.entity.account;

import Utils.Logger;
import Utils.Result;
import bankBackend.Constants;
import bankBackend.entity.Balance;
import bankBackend.entity.InterestRate;
import bankBackend.entity.Transaction;
import bankBackend.entity.User;
import bankBackend.entity.enums.AccountType;
import bankBackend.entity.enums.CurrencyType;
import bankBackend.entity.enums.RateType;
import bankBackend.entity.enums.TransactionType;
import bankBackend.service.SvcMgr;
import bankBackend.service.impl.UserCtl;

import java.util.List;

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

    private static Result<Transaction> createTransaction(int fromBalanceId, int value) {
        Account managerAccount = UserCtl.getInstance().getManager().getAccount(AccountType.SAVINGS).unwrap();
        // get manager's saving account
        return Transaction.makeTransaction(fromBalanceId, managerAccount.getId(), TransactionType.TRANSFER, value);
    }
    public static void generateLoanInterestCallback(int date, int hour) {
        Logger.info("Generating loan interest...");
        List<LoanAccount> accounts = (List<LoanAccount>) Account.listAccountByType(AccountType.Loan);
        for (LoanAccount account : accounts) {
            Result<InterestRate> irRes = SvcMgr.getInterestRateService().getInterestRate(account.getId(), RateType.Loan);
            if (!irRes.success) continue;
            InterestRate ir = irRes.data;
            for (Balance balance : account.listBalances()) {
                if (balance.getType() == CurrencyType.USD) {
                    float rat = ir.getRate() / 100;
                    int deltaValue = (int) (balance.getValue() * rat);
                    Result<Transaction> txRes = createTransaction(account.getId(), deltaValue);
                    if (!txRes.success) {
                        Logger.error("Failed to create transaction for interest on user loan account" + account.getId());
                    }
                    Result r = account.handleTransaction(txRes.data);
                    if (!r.success) {
                        Logger.error("Failed to charge interest for loan account " + account.getId() + ": " + r.msg);
                    }
                }
            }
        }
    }
}
