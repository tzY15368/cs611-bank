package bankBackend.entity.account;

import Utils.Logger;
import Utils.Result;
import bankBackend.Config;
import bankBackend.entity.InterestRate;
import bankBackend.entity.User;
import bankBackend.entity.enums.AccountType;
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


    public static void generateLoanInterestCallback(int date, int hour) {
        Logger.info("Generating loan interest...");
        List<Account> accounts = SvcMgr.getAccountService().listAccountByType(AccountType.Loan);
        int currentEpoch = SvcMgr.getDateTimeService().getCurrentEpoch();
        for (Account account : accounts) {

            List<InterestRate> rates = SvcMgr.getInterestRateService().getInterestRate(account.getId(), RateType.Loan);
            rates.forEach(rate -> {
                int delta = rate.getDeltaForEpoch(currentEpoch);
                Result r = SvcMgr.getAccountService().createAndHandleTxn(
                        Config.TXN_NULL_SENDER,
                        account.getId(),
                        TransactionType.INTEREST,
                        delta,
                        "Loan Interest of loan" + rate.getId(),
                        rate.getCurrency()
                );

                if (!r.success) {
                    Logger.error("Failed to generate loan interest for loan " + rate.getId());
                }
                int deltaVal = rate.getLoanPay();
                User u = SvcMgr.getUserService().getUserByName(SvcMgr.getUserService().getUsernameByAccountId(account.getId())).unwrap();
//                Result r2 = SvcMgr.getAccountService().createAndHandleTxn(
//                        u.getSavingAccount().unwrap().getId(),
//                        ,
//                        TransactionType.TRANSFER,
//                        deltaVal,
//                        "Loan pay of loan" + rate.getId(),
//                        rate.getCurrency()
//                );
//                if(!r2.success){
//                    Logger.error("Failed to generate loan pay for loan " + rate.getId());
//                }
//                Result r3 = SvcMgr.getAccountService().createAndHandleTxn(
//                        loanAcc,
//                        Config.TXN_NULL_RECEIVER,
//                        TransactionType.TRANSFER,
//                        deltaVal,
//                        "loan pay of loan" + rate.getId(),
//                        rate.getCurrency()
//                );
//                if(!r3.success){
//                    Logger.error("Failed to generate loan pay for loan " + rate.getId());
//                }
            });
        }
    }
}
