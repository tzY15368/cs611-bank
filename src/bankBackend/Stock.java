package bankBackend;

import Utils.DBManager;
import Utils.Logger;

import Utils.Result;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "Stock")

public class Stock {
    static Dao<Stock, Integer> dao = DBManager.getDao(Stock.class);

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(unique = true)
    private String name;

    @DatabaseField
    private int userId;

    @DatabaseField
    private int buyInPrice;

    @DatabaseField
    private int currentPrice;

    @DatabaseField
    private int amount;

    @DatabaseField
    private int realizedProfit;

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setBuyInPrice(int buyInPrice) {
        this.buyInPrice = buyInPrice;
    }

    public int getBuyInPrice() {
        return buyInPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public Result<Void> addRealizedProfit(int realizedProfit) {
        this.realizedProfit += realizedProfit;
        return new Result<>(true, "", null);
    }

    public int getRealizedProfit() {
        return realizedProfit;
    }
}
