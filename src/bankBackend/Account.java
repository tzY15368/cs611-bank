package bankBackend;

import Utils.DBManager;
import Utils.Logger;
import Utils.Result;
import Utils.SessionMgr;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

// not a table as this is inherited
public abstract class Account {

    public static Dao<Account, Integer> dao = DBManager.getDao(Account.class);

    @DatabaseField
    protected int id;
    @DatabaseField
    protected int userId;

    public Account() {
        // ORMLite needs a no-arg constructor
    }

    public Account(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    // if there's no userId, use the one in the session
    public static List<Account> listAccount() {
        int currentUserId = SessionMgr.getSession().getData().getUser().getId();
        return listAccount(currentUserId);
    }

    public static List<Account> listAccount(int userId) {
        try {
            List<Account> accounts = Account.dao.queryBuilder().selectColumns("id")
                    .where().eq("userId", userId).query();
            return accounts;
        } catch (Exception e) {
            Logger.error("listAccount:" + e.getMessage());
        }
        return new ArrayList<>();
    }

    public String getReport() {
        return null;
    }

    // adding and withdrawing money is not an atomic operation
    // but they share the same deltaBalance method
    // also if a balance is not found, it will be created
    // interest rates are calculated in the INHERITED CLASSES
    public Result<Void> deltaBalance(int value, CurrencyType kind) {
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
