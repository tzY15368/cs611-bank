package bankBackend.entity.account;

import bankBackend.entity.enums.AccountState;
import bankBackend.entity.Balance;
import bankBackend.dao.DaoManager;
import Utils.Logger;
import Utils.Result;
import bankBackend.entity.Transaction;
import bankBackend.entity.enums.AccountType;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@DatabaseTable(tableName = "Accounts")
public abstract class Account {

    static Dao<Account, Integer> dao = DaoManager.getDao(Account.class);

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

    public String getReport() {
        return null;
    }

    public AccountState getState() {
        return state;
    }

    // TODO: IMPLEMENT THIS
    public Result<Void> handleTransaction(Transaction tx) {
        if (tx.toAccountId == this.id) {
            // ...
            // TODO: HANDLE TRANSACTION
        }
        Balance balance = null;
        try {
            balance = Balance.dao.queryForId(tx.fromBalanceId);
        } catch (SQLException e) {
            Logger.error("handleTransaction:" + e.getMessage());
        }
        if (balance == null) {
            return new Result<>(false, "Balance not found", null);
        }
        // ...
        // TODO: HANDLE TRANSACTION
        return balance.deltaValue(tx.value);
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

    // adding and withdrawing money is not an atomic operation
    // but they share the same deltaBalance method
    // also if a balance is not found, it will be created
    // interest rates are calculated in the INHERITED CLASSES
    private Result<Void> deltaBalance(int value, CurrencyType kind) {
        if (value < 0) {
            return new Result<>(false, "Cannot add negative balance", null);
        }
        //if exist, add money, if not exist, create new balance
        Result<Balance> res = Balance.getBalanceWithCurrency(this, kind);

        if (!res.isSuccess()) {
            return new Result<>(false, "addBalance: " + res.getMsg(), null);
        }
        Balance balance = res.getData();
        if (balance == null) {
            Result<Balance> newRes = Balance.createBalance(this, kind);
            if (!newRes.isSuccess()) {
                return new Result<>(false, "addBalance: " + newRes.getMsg(), null);
            }
            balance = newRes.getData();
        }
        assert balance != null;
        Result addResult = balance.deltaValue(value);
        if (!addResult.isSuccess()) {
            return new Result<>(false, "addBalance: " + addResult.getMsg(), null);
        }
        try {
            Balance.dao.update(balance);
            return new Result<>(true, "addBalance: success", null);
        } catch (Exception e) {
            Logger.error("addBalance: " + e.getMessage());
            return new Result<>(false, "addBalance: " + e.getMessage(), null);
        }
    }
}
