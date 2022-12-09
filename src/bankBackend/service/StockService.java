package bankBackend.service;

import Utils.Result;
import bankBackend.entity.Stock;
import bankBackend.entity.User;

import java.util.List;

public interface StockService {
    public List<Stock> listStocks(int userId);

    public Result<Void> addStock(Stock stock);

    public Result<Void> removeStock(String stockName);

    public Result<Void> updateStock(Stock stock);

    public Result<Void> buyStock(String name, User user, int amount);

    public Result<Void> sellStock(String name, User user, int amount);

    public Result<Integer> getRealizedProfit(String name, User user);

    public Result<Integer> getUnrealizedProfit(String name, User user);
}
