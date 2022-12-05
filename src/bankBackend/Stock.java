package bankBackend;

import Utils.DBManager;
import Utils.Logger;
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
}
