package bankBackend.entity.account;

import Utils.Logger;
import Utils.Result;
import bankBackend.entity.Balance;
import bankBackend.entity.InterestRate;
import bankBackend.entity.enums.*;
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

    public static void generateInterestCallback(int date, int hour) {
        Logger.info("Generating interest...");
        List<Account> accounts = SvcMgr.getAccountService().listAccountByType(AccountType.SAVINGS);
        int currentEpoch = SvcMgr.getDateTimeService().getCurrentEpoch();

        //the interest is paid by the manager's checking account
        Account managerAccount = UserCtl.getInstance().getManager().getAccount(AccountType.CHECKING).unwrap();

        for (Account account : accounts) {
            if (account.getState() != AccountState.OPEN) {
                continue;
            }
            // get the saving account's balances
            List<InterestRate> rates = SvcMgr.getInterestRateService().getInterestRate(account.getId(), RateType.Save);
            rates.forEach(rate -> {
                // calculate delta
                int delta = rate.getDeltaForEpoch(currentEpoch);
                // get the manager's balance of the currency
                Balance savingBalance = Balance.getBalanceWithCurrency(managerAccount.getId(), rate.getCurrency()).unwrap();
                // create transaction
                Result r = SvcMgr.getAccountService().createAndHandleTxn(
                        savingBalance.getId(),
                        account.getId(),
                        TransactionType.INTEREST,
                        delta,
                        "Saving Interest of saving" + rate.getId(),
                        rate.getCurrency()
                );
                if (!r.success) {
                    Logger.error("Failed to generate interest for account " + account.getId() + ": " + r.msg);
                }
            });
        }
    }
}
