package bankBackend.entity.account;

import bankBackend.Constants;
import bankBackend.entity.enums.AccountState;
import bankBackend.entity.Balance;
import bankBackend.dao.DaoManager;
import Utils.Logger;
import Utils.Result;
import bankBackend.entity.Transaction;
import bankBackend.entity.enums.AccountType;
import bankBackend.entity.enums.CurrencyType;
import bankBackend.entity.enums.TransactionType;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@DatabaseTable(tableName = "Accounts")
public abstract class Account {

    public static Dao<Account, Integer> dao = DaoManager.getDao(Account.class);

    @DatabaseField
    protected int id;
    @DatabaseField
    protected int userId;

    @DatabaseField
    protected AccountType type;

    @DatabaseField
    protected AccountState state;

    public Account(int userId, AccountType type) {
        this.userId = userId;
        this.type = type;
        this.state = AccountState.INACTIVE;
    }

    public Account() {
        // ORMLite needs a no-arg constructor
    }

    public int getId() {
        return id;
    }

    public AccountState getState() {
        return state;
    }

    public Result<Void> setState(AccountState state) {
        this.state = state;
        try {
            dao.update(this);
            Balance thisUSDBalance = Balance.getBalanceWithCurrency(this, CurrencyType.USD).data;
            Result<Transaction> txRes = Transaction.makeTransaction(
                    thisUSDBalance.getId(),
                    0,
                    TransactionType.CHARGE_FEE,
                    Constants.TX_CHARGE_FEE_VALUE,
                    "Account state change fee"
            );
            if (!txRes.success) {
                return new Result(false, "Failed to charge fee:" + txRes.msg, null);
            }
            Result r = this.handleTransaction(txRes.data);
            return r;
        } catch (SQLException e) {
            return new Result<>(false, "SQL Exception in setState:" + e + ": " + e.getMessage(), null);
        }
    }

    // only do the transcation in the sending side (fromBalance)
    // IMPORTANT: Only call this once from the sending side
    public Result<Void> handleTransaction(Transaction tx) {
        Logger.info(String.format("Account: handling tx %s", tx.toString()));
        Balance srcBalance = Balance.getBalanceById(tx.fromBalanceId);
        Balance dstBalance = Balance.getBalanceById(Balance.getBalanceWithCurrency(this, srcBalance.getType()).data.getId());
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

    public List<Balance> listBalance() {
        try {
            List<Balance> balances = Balance.dao.queryBuilder().selectColumns("id")
                    .where().eq("accountId", this.id).query();
            return balances;
        } catch (Exception e) {
            Logger.error("listBalances:" + e.getMessage());
        }
        return new ArrayList<>();
    }

    public static Account getAccountById(int id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            Logger.error("getAccountById:" + e.getMessage());
        }
        return null;
    }

    public static List<?> listAccountByType(AccountType type) {
        try {
            return dao.queryBuilder()
                    .where().eq("type", type).query();
        } catch (Exception e) {
            Logger.error("listAccountByType:" + e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<Balance> listBalances() {
        try {
            return Balance.dao.queryBuilder()
                    .where().eq("accountId", this.id).query();
        } catch (Exception e) {
            Logger.error("listBalances:" + e.getMessage());
        }
        return new ArrayList<>();
    }
}
