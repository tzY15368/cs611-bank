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

enum CurrencyType {
    USD,
    EUR,
    GBP,
}

@DatabaseTable(tableName = "Balances")
public class Balance {

    public static Dao<Balance, Integer> dao = DBManager.getDao(Balance.class);

    @DatabaseField
    private int accountId;

    @DatabaseField(generatedId = true)
    private int id;

    // note that Money has a 100x ratio: 1$ is 100 in int value
    @DatabaseField
    private int value;

    @DatabaseField
    private CurrencyType type;

    public Balance() {
        // ORMLite needs a no-arg constructor
    }

    public Balance(CurrencyType kind) {
        this.value = 0;
        this.type = kind;
    }

    public int getValue() {
        return value;
    }

    public CurrencyType getType() {
        return type;
    }

    // ACID no more
    public static List<Balance> listBalance(Account account) {
        try {
            List<Balance> balances = Balance.dao.queryBuilder().selectColumns("id")
                    .where().eq("accountId", account.getId()).query();
            return balances;
        } catch (Exception e) {
            Logger.error("listBalances:" + e.getMessage());
        }
        return new ArrayList<>();
    }


    public Result<Void> deltaValue(int value) {
        if (this.value + value < 0) {
            return new Result<>(false, "Cannot set negative balance", null);
        }
        this.value += value;
        return new Result<>(true, "", null);
    }


    public static Result<Balance> getBalanceWithCurrency(Account account, CurrencyType kind) {
        try {
            List<Balance> balances = Balance.dao.queryBuilder().selectColumns("id")
                    .where().eq("accountId", account.getId()).and().eq("type", kind).query();
            if (balances.size() == 0) {
                return new Result<>(true, "no balance with this currency", null);
            }
            return new Result<>(true, "success", balances.get(0));
        } catch (Exception e) {
            Logger.error("getBalanceWithCurrency:" + e.getMessage());
        }
        return new Result<>(false, "getBalanceWithCurrency failed", null);
    }

    public static Result<Balance> createBalance(Account account, CurrencyType kind) {
        try {
            Balance balance = new Balance(kind);
            balance.accountId = account.getId();
            Balance.dao.create(balance);
            return new Result<>(true, "success", balance);
        } catch (Exception e) {
            Logger.error("createBalance:" + e.getMessage());
        }
        return new Result<>(false, "createBalance failed", null);
    }
}
