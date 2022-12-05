package bankBackend;

import Utils.DBManager;
import Utils.Logger;
import Utils.Result;
import Utils.SessionMgr;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

enum AccountType {
    CHECKING,
    SAVINGS,
    Loan,
    Security
}

@DatabaseTable(tableName = "Accounts")
public class Account {

    static Dao<Account, Integer> dao = DBManager.getDao(Account.class);

    @DatabaseField
    protected int id;
    @DatabaseField
    protected int userId;

    @DatabaseField
    protected AccountType type;

    public Account() {
        // ORMLite needs a no-arg constructor
    }

    public Account(int userId, AccountType type) {
        this.userId = userId;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public static Account getAccount(int userId, AccountType type) {
        try {
            List<Account> accounts = dao.queryBuilder().where().eq("userId", userId).and().eq("type", type).query();
            if (accounts.size() == 0) {
                Logger.fatal("Account not found, this should not happen");
                return null;
            }
            return accounts.get(0);
        } catch (SQLException e) {
            Logger.error(e.getMessage());
            return null;
        }
    }

    public String getReport() {
        return null;
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
        if (!res.success) {
            return new Result<>(false, "addBalance: " + res.msg, null);
        }
        Balance balance = res.data;
        if (balance == null) {
            Result<Balance> newRes = Balance.createBalance(this, kind);
            if (!newRes.success) {
                return new Result<>(false, "addBalance: " + newRes.msg, null);
            }
            balance = newRes.data;
        }
        assert balance != null;
        Result addResult = balance.deltaValue(value);
        if (!addResult.success) {
            return new Result<>(false, "addBalance: " + addResult.msg, null);
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
