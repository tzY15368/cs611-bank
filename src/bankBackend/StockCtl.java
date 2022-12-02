package bankBackend;

import Utils.Logger;
import Utils.Result;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockCtl {

    public static List<Stock> listStocks() {
        try {
            Stock.dao.queryForAll();
        } catch (SQLException e) {
            Logger.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    public static void addStock(Stock stock) {
        try {
            Stock.dao.createIfNotExists(stock);
        } catch (SQLException e) {
            Logger.error(e.getMessage());
        }
    }

    public static void removeStock(Stock stock) {
        try {
            Stock.dao.delete(stock);
        } catch (SQLException e) {
            Logger.error(e.getMessage());
        }
    }

    public static Result<Void> buyStock(Stock stock, User user, int amount) {
        return null;
    }

    public static Result<Void> sellStock(Stock stock, User user, int amount) {
        return null;
    }
}
