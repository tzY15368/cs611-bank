package bankBackend.entity;

import Utils.Logger;
import Utils.Result;
import bankBackend.entity.account.Account;
import bankBackend.service.SvcMgr;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Report {
    private int epoch;
    private List<Transaction> transactionList;

    public Report(int epoch) {
        this.epoch = epoch;
        this.transactionList = new ArrayList<>();
    }

    // get report within xx epoch
    public static Result<Report> getReport(User user, int epoch) {
        Report r = new Report(epoch);
        Result<Void> res = r.setTransactions(user);
        if (!res.success) {
            return new Result<>(false, res.msg, null);
        }
        return new Result<>(r);
    }

    private Result<Void> setTransactions(User user) {
        // list the user's all accounts and their balance ids
        List<Account> accounts = SvcMgr.getUserService().listAccount(user);
        List<Integer> accountIds = new ArrayList<>();
        List<Integer> balanceIds = new ArrayList<>();
        accounts.forEach(acc -> accountIds.add(acc.getId()));
        for (Account acc : accounts) {
            SvcMgr.getAccountService().listBalance(acc.getId()).forEach(b -> balanceIds.add(b.getId()));
        }
        Logger.info("user accounts: " + accountIds);
        Logger.info("user balances: " + balanceIds);
        int nowEpoch = SvcMgr.getDateTimeService().getCurrentEpoch();
        try {
            this.transactionList = Transaction.dao.queryBuilder().where().gt("epoch", nowEpoch - this.epoch).and()
                    .in("fromBalanceId", balanceIds)
                    .or().in("toAccountId", accountIds).and().gt("epoch", nowEpoch - this.epoch).query();
            Logger.info("Report: " + this.transactionList.size() + " transactions found");
            return new Result<>();
        } catch (SQLException e) {
            Logger.error("SQL Exception in getTransaction:" + e + e.getMessage());
            return new Result<>(false, "getTransaction: " + e.getMessage(), null);
        }
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }
}
