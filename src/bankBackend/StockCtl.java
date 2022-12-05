package bankBackend;

import Utils.Logger;
import Utils.Result;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockCtl {

    // either this or singleton pattern

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

    public static Result<Void> updateStock(Stock stock) {
        return null;
    }

    public static Result<Void> buyStock(String name, User user, int amount) {
        return null;
    }

    public static Result<Void> sellStock(String name, User user, int amount) {
        return null;
    }

    public static Result<Integer> getRealizedProfit(String name, User user) {
        return null;
    }

    public static Result<Integer> getUnrealizedProfit(String name, User user) {
        return null;
    }
}
