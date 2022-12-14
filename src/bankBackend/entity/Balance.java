package bankBackend.entity;

import Utils.*;
import bankBackend.dao.DaoManager;
import bankBackend.entity.account.Account;
import bankBackend.entity.enums.CurrencyType;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;


@DatabaseTable(tableName = "Balances")
public class Balance {

    public static Dao<Balance, Integer> dao = DaoManager.getDao(Balance.class);

    @DatabaseField
    private int accountId;

    @DatabaseField(generatedId = true)
    private int id;

    // note that Money has a 100x ratio: 1$ is 100 in int value
    @DatabaseField
    private int value;

    @DatabaseField
    private CurrencyType type;

    public String toString() {
        return String.format("Balance[id=%d, accountId=%d, value=%d, type=%s]", id, accountId, value, type);
    }

    public Balance() {
        // ORMLite needs a no-arg constructor
    }

    public Balance(CurrencyType kind) {
        this.value = 0;
        this.type = kind;
    }

    public int getId() {
        return id;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getValue() {
        return value;
    }

    public CurrencyType getType() {
        return type;
    }

    public Result<Void> deltaValue(int value) {
        if (this.value + value < 0) {
            return new Result<>(false, "Cannot set negative balance", null);
        }
        try {
            this.value += value;
            System.out.println("this.accountid:" + this.accountId);
            dao.update(this);
            return new Result<>(true, "Balance updated", null);
        } catch (Exception e) {
            return new Result<>(false, "Error updating balance", null);
        }
    }

    public static Result<Balance> getBalanceWithCurrency(int accountId, CurrencyType kind) {
        try {
            List<Balance> balances = Balance.dao.queryBuilder()
                    .where().eq("accountId", accountId).and().eq("type", kind).query();
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

    public static Balance getBalanceById(int id) {
        try {
            Balance b = Balance.dao.queryForId(id);
            Logger.warn(b.toString());
            return b;
        } catch (Exception e) {
            Logger.fatal("getBalanceById:" + e.getMessage());
        }
        return null;
    }
}
