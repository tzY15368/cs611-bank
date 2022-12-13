package bankBackend.service.impl;

import Utils.Logger;
import Utils.Result;
import bankBackend.entity.Balance;
import bankBackend.entity.Transaction;
import bankBackend.entity.account.Account;
import bankBackend.entity.enums.AccountType;
import bankBackend.service.AccountService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountCtl implements AccountService {

    private static AccountCtl instance;

    private AccountCtl() {
    }

    public static AccountCtl getInstance() {
        if (instance == null) {
            instance = new AccountCtl();
        }
        return instance;
    }

    @Override
    public Account getAccountById(int accountId) {
        try {
            return Account.dao.queryForId(accountId);
        } catch (SQLException e) {
            Logger.error("getAccountById:" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Account> listAccountByType(AccountType type) {
        try {
            return Account.dao.queryBuilder()
                    .where().eq("type", type).query();
        } catch (Exception e) {
            Logger.error("listAccountByType:" + e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public Result<Void> handleTxn(Transaction tx) {
        Logger.info(String.format("Account: handling tx %s", tx.toString()));
        Balance srcBalance = Balance.getBalanceById(tx.fromBalanceId);
        Balance dstBalance = Balance.getBalanceById(Balance.getBalanceWithCurrency(
                tx.toAccountId,
                srcBalance.getType()).data.getId()
        );
        Result r = srcBalance.deltaValue(-tx.value);
        if (!r.success) {
            return r;
        }
        r = dstBalance.deltaValue(tx.value);
        if (!r.success) {
            return r;
        }
        // write to disk
        try {
            Balance.dao.update(srcBalance);
            Balance.dao.update(dstBalance);
            Transaction.dao.create(tx);
        } catch (SQLException e) {
            return new Result<>(false, "SQL Exception in handleTransaction:" + e + ": " + e.getMessage(), null);
        }
        return new Result<>(true, "Transaction successful", null);
    }

    @Override
    public List<Balance> listBalance(int accountId) {
        try {
            List<Balance> balances = Balance.dao.queryBuilder()
                    .where().eq("accountId", accountId).query();
            return balances;
        } catch (Exception e) {
            Logger.error("listBalances:" + e.getMessage());
        }
        return new ArrayList<>();
    }
}
