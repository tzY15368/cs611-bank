package bankBackend.entity.account;

import Utils.Logger;
import Utils.Result;
import bankBackend.Constants;
import bankBackend.entity.Balance;
import bankBackend.entity.InterestRate;
import bankBackend.entity.Transaction;
import bankBackend.entity.enums.AccountType;
import bankBackend.entity.enums.CurrencyType;
import bankBackend.entity.enums.RateType;
import bankBackend.entity.enums.TransactionType;
import bankBackend.service.SvcMgr;
import bankBackend.service.impl.UserCtl;

import java.util.List;

public class SavingAccount extends Account {

    public SavingAccount() {
        // ORMLite needs a no-arg constructor
        super(-1, AccountType.SAVINGS);
    }

    public SavingAccount(int userId) {
        super(userId, AccountType.SAVINGS);
    }


    private static Result<Transaction> createTransaction(int toAccount, int value) {
        // get manager's saving account
        Account managerAccount = UserCtl.getInstance().getManager().getAccount(AccountType.CHECKING).unwrap();
        // get manager's saving account's USD balance
        Balance managerUSDBalance = Balance.getBalanceWithCurrency(managerAccount, CurrencyType.USD).unwrap();
        // create transaction
        return Transaction.makeTransaction(
                managerUSDBalance.getId(),
                toAccount,
                TransactionType.TRANSFER,
                value,
                "Saving Interest"
        );
    }

    public static void generateInterestCallback(int date, int hour) {
        Logger.info("Generating interest...");
        List<Account> accounts = Account.listAccountByType(AccountType.SAVINGS);
        for (Account account : accounts) {
            Result<InterestRate> irRes = SvcMgr.getInterestRateService().getInterestRate(account.getId(), RateType.Save);
            if (!irRes.success) continue;
            InterestRate ir = irRes.data;
            for (Balance balance : account.listBalance()) {
                if (balance.getType() == CurrencyType.USD && balance.getValue() > Constants.SAVING_ACC_INTEREST_THRESHOLD) {
                    float rat = ir.getRate() / 100;
                    int deltaValue = (int) (balance.getValue() * rat);
                    Result<Transaction> txRes = createTransaction(account.getId(), deltaValue);
                    if (!txRes.success) {
                        Logger.error("Failed to create transaction for interest on user account" + account.getId());
                    }
                    Result r = account.handleTransaction(txRes.data);
                    if (!r.success) {
                        Logger.error("Failed to generate interest for account " + account.getId() + ": " + r.msg);
                    }
                }
            }
        }
    }
}
