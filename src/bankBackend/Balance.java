package bankBackend;

import Utils.Result;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

enum CurrencyType {
    USD,
    EUR,
    GBP,
}

@DatabaseTable(tableName = "Balances")
public class Balance {
    @DatabaseField
    private int userId;
    @DatabaseField
    private double value;
    @DatabaseField
    private CurrencyType type;

    public Balance(double value, CurrencyType kind) {
        this.value = value;
        this.type = kind;
    }

    public Result<Void> addBalance(double value, String kind) {
        //if exist, add money, if not exist, create new balance
        return null;
    }

    public Result<Void> minusBalance(double value, String kind) {
        return null;
    }

    public double findBalance(String kind) {
        return value;
    }


}
