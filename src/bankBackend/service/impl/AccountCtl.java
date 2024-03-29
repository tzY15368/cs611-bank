package bankBackend.service.impl;

import Utils.Logger;
import Utils.Result;
import bankBackend.Config;
import bankBackend.entity.Balance;
import bankBackend.entity.Transaction;
import bankBackend.entity.User;
import bankBackend.entity.account.Account;
import bankBackend.entity.enums.AccountState;
import bankBackend.entity.enums.AccountType;
import bankBackend.entity.enums.CurrencyType;
import bankBackend.entity.enums.TransactionType;
import bankBackend.service.AccountService;
import bankBackend.service.SvcMgr;

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
    public Result<Void> createAndHandleTxn(int fromBalanceId, int toAccountId, TransactionType type, int value, String description, CurrencyType ct) {
        Transaction txn = new Transaction(fromBalanceId, toAccountId, type, value, description, ct);
        Logger.info("Created transaction: " + txn.toString());
        int chargeFee = 0;
        int feeChargeBalanceId = fromBalanceId;
        if (Config.ALL_CHARGE_FEE_VALUES.containsKey(type)) {
            chargeFee = Config.ALL_CHARGE_FEE_VALUES.get(type);
            if (value < chargeFee) {
                return new Result<>(false, "Transaction value is too small to cover the charge fee", null);
            }
        }

        Account a = SvcMgr.getAccountService().getAccountById(txn.toAccountId);
        if (a != null) {
            if (a.getState() != AccountState.OPEN) {
                return new Result<>(false, "Account is not open", null);
            }
        }
        Result r;
        // do the transaction itself
        if (txn.fromBalanceId != Config.TXN_NULL_SENDER) {
            Balance b = Balance.getBalanceById(txn.fromBalanceId);
            r = b.deltaValue(-txn.value);
            if (!r.success) {
                return r;
            }
        }
        if (txn.toAccountId != Config.TXN_NULL_RECEIVER) {
            Balance b = Balance.getBalanceWithCurrency(txn.toAccountId, txn.currencyType).unwrap();
            r = b.deltaValue(txn.value);
            if (!r.success) {
                return r;
            }
            if (txn.fromBalanceId == Config.TXN_NULL_SENDER) {
                feeChargeBalanceId = b.getId();
            }
        }
        try {
            Transaction.dao.create(txn);
        } catch (SQLException e) {
            return new Result<>(false, "SQL Exception in createAndHandleTxn:" + e + ": " + e.getMessage(), null);
        }

        // if necessary, do the charge fee transaction
        if (chargeFee > 0) {
            // get the bank manager
            User mgr = UserCtl.getInstance().getManager(Config.BANK_MANAGER_USERNAME);
            // get mgr's checking account
            Account checking = mgr.getCheckingAccount().unwrap();
            r = createAndHandleTxn(
                    feeChargeBalanceId,
                    checking.getId(),
                    TransactionType.CHARGE_FEE,
                    chargeFee,
                    "Charge fee for transaction " + txn.getId(),
                    txn.currencyType
            );
            return r;
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

    @Override
    public void rollbackTxn(int epoch) {
        Logger.warn("Rolling back transactions from epoch " + epoch);
        Logger.warn("not implemented yet");
    }
}
