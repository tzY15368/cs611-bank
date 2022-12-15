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
        Balance managerUSDBalance = Balance.getBalanceWithCurrency(managerAccount.getId(), CurrencyType.USD).unwrap();
        return Transaction.makeTransaction(fromBalanceId, managerAccount.getId(), TransactionType.INTEREST, value,"Loan Interest");
    }
    public static void generateLoanInterestCallback(int date, int hour) {
        Logger.info("Generating loan interest...");
        List<Account> accounts = SvcMgr.getAccountService().listAccountByType(AccountType.Loan);
        for (Account account : accounts) {
            Result<InterestRate> irRes = SvcMgr.getInterestRateService().getInterestRate(account.getId(), RateType.Loan);
            if (!irRes.success) continue;
            InterestRate ir = irRes.data;
            for (Balance balance : SvcMgr.getAccountService().listBalance(account.getId())) {
                if (balance.getType() == CurrencyType.USD) {
                    float rat = ir.getRate() / 100;
                    int deltaValue = (int) (balance.getValue() * rat);
                    Result<Transaction> txRes = createTransaction(account.getId(), deltaValue);
                    System.out.println("txRes message" + txRes.msg);
                    if (!txRes.success) {
                        Logger.error("Failed to create transaction for interest on user loan account" + account.getId());
                    }
                    Result r = SvcMgr.getAccountService().handleTxn(txRes.data);
                    if (!r.success) {
                        Logger.error("Failed to charge interest for loan account " + account.getId() + ": " + r.msg);
                    }
                }
            }
        }
    }
}
