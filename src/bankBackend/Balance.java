package bankBackend;

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

    public void addBalance(double value, String kind) {
        //if exist, add money, if not exist, create new balance
    }

    public void minusBalance(double value, String kind) {

    }

    public double findBalance(String kind) {
        return value;
    }


}
