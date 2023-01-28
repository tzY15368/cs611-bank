package bankBackend.service.impl;

import bankBackend.Config;
import Utils.Logger;
import Utils.Result;
import bankBackend.entity.Balance;
import bankBackend.entity.User;
import bankBackend.entity.Stock;
import bankBackend.entity.account.Account;
import bankBackend.entity.enums.AccountType;
import bankBackend.entity.enums.CurrencyType;
import bankBackend.entity.enums.TransactionType;
import bankBackend.factory.ManagerFactory;
import bankBackend.service.AccountService;
import bankBackend.service.StockService;
import bankBackend.service.SvcMgr;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static bankBackend.Config.STOCK_MANAGER_USER_ID;

public class StockCtl implements StockService {

    private static StockCtl instance = null;

    private StockCtl() {
    }

    public static StockCtl getInstance() {
        if (instance == null) {
            instance = new StockCtl();
        }
        return instance;
    }

    public static void init() {
        // if there's no stock manager, create one
        ManagerFactory mf = new ManagerFactory();
        Result r = mf.createUser(Config.STOCK_MARKET_NAME, "manager");
        if (!r.success) {
            Logger.warn("StockCtl.init: " + r.msg);
        }
        User mgr;
        try {

            mgr = User.dao.queryBuilder().where().eq("name", Config.STOCK_MARKET_NAME).queryForFirst();
        } catch (SQLException e) {
            Logger.fatal("StockCtl.init: " + e.getMessage());
            return;
        }
        if (mgr == null) {
            Logger.fatal("StockCtl.init: cannot find stock manager");
        }
        Config.STOCK_MANAGER_USER_ID = mgr.getId();
        Logger.info("Stock manager initialized, mgr id = " + mgr.getId());
    }

    public List<Stock> listStocks(int userId) {
        try {
            List<Stock> stocks = Stock.dao.queryBuilder().where().eq("userId", userId).query();
            return stocks;
        } catch (SQLException e) {
            Logger.error("SQL Exception in listStock:" + e + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Result<Void> addStock(Stock stock) {
        try {
            Stock s = Stock.dao.queryBuilder().where().eq("name", stock.getName()).queryForFirst();
            if (s != null) {
                return new Result<>(false, "stock already exists ", null);
            }
            Stock.dao.createIfNotExists(stock);
        } catch (SQLException e) {
            Logger.error("SQL Exception in addStock:" + e + e.getMessage());
            return new Result<>(false, "addStock: " + e.getMessage(), null);
        }
        return new Result<>(true, "addStock: success ", null);
    }

    public Result<Void> removeStock(String stockName) {
        try {
            Stock stock = Stock.dao.queryBuilder().where().eq("name", stockName).and()
                    .eq("userId", Config.STOCK_MANAGER_USER_ID).queryForFirst();
            if (stock == null) {
                return new Result<>(false, "Stock doesn't exist", null);
            }
            Stock.dao.delete(stock);
        } catch (SQLException e) {
            Logger.error("SQL Exception in removeStock:" + e + e.getMessage());
            return new Result<>(false, "removeStock: " + e + ": " + e.getMessage(), null);
        }
        return new Result<>(true, "removeStock: success ", null);
    }

    public Result<Void> updatePrice(String name, int value) {
        try {
            Stock.dao.updateRaw("update stock set price = ? where name = ?", "" + value, name);
        } catch (SQLException e) {
            Logger.error("SQL Exception in updateStock:" + e + e.getMessage());
            return new Result<>(false, "addStock: " + e.getMessage(), null);
        }
        return new Result<>(true, "addStock: success ", null);
    }

    public Result<Void> buyStock(String name, User user, int amount) {
        try {
            Stock stock = Stock.dao.queryBuilder().where().eq("name", name)
                    .and().eq("userId", STOCK_MANAGER_USER_ID).queryForFirst();
            //check if user can buy stock
            if (stock == null) {
                return new Result<>(false, "Stock doesn't exist", null);
            }
            if (stock.getAmount() < amount) {
                return new Result<>(false, "Stock's amount is not enough", null);
            }

            // assume we can only use USD to buy stock, reset user's money

            int fromBalanceId = Balance.getBalanceWithCurrency(user.getAccount(AccountType.Security).unwrap().getId(), CurrencyType.USD).unwrap().getId();
            Account account = Account.dao.queryBuilder().where().eq("userId", STOCK_MANAGER_USER_ID)
                    .and().eq("type", AccountType.Security).queryForFirst();
            int toAccountID = account.getId();
            int value = amount * stock.getCurrentPrice();
            Result r = SvcMgr.getAccountService().createAndHandleTxn(
                    fromBalanceId,
                    toAccountID,
                    TransactionType.STOCK,
                    value,
                    "stockbuy=" + name,
                    CurrencyType.USD
            );
            if (!r.success) {
                return r;
            }

            // reset the amount of stock in the market
            stock.setAmount(stock.getAmount() - amount);
            Stock.dao.update(stock);

            // give the stock to user
            Stock s = new Stock();
            s.setAmount(amount);
            s.setUserId(user.getId());
            s.setName(name);
            s.setBuyInPrice(stock.getCurrentPrice());
            Stock.dao.create(s);
            return new Result<>(true, "buyStock: success: ", null);
        } catch (SQLException e) {
            Logger.error("SQL Exception in buyStock:" + e + e.getMessage());
            return new Result<>(false, "buyStock: " + e + e.getMessage(), null);
        }
    }

    public Result<Void> sellStock(String name, User user, int amount) {
        try {
            List<Stock> stockList = Stock.dao.queryBuilder().where().eq("name", name)
                    .and().eq("userId", user.getId()).query();
            //check if user can sell stock
            if (stockList == null) {
                return new Result<>(false, "Stock doesn't exist", null);
            }
            //check if user have enough amount stock
            int totalAmount = 0;
            for (int i = 0; i < stockList.size(); i++) {
                totalAmount += stockList.get(i).getAmount();
            }
            if (totalAmount < amount) {
                return new Result<>(false, "Doesn't have enough amount to sell", null);
            }

            //give user sell stock money
            Stock s = Stock.dao.queryBuilder().where().eq("name", name).and()

                    .eq("userId", STOCK_MANAGER_USER_ID).queryForFirst();
            int fromBalanceId = Balance.dao.queryBuilder().where().eq("userId", STOCK_MANAGER_USER_ID)
                    .and().eq("type", CurrencyType.USD).queryForFirst().getId();
            int toAccountID = user.getAccount(AccountType.Security).unwrap().getId();
            int value = s.getCurrentPrice() * amount;
            Result r = SvcMgr.getAccountService().createAndHandleTxn(
                    fromBalanceId,
                    toAccountID,
                    TransactionType.STOCK,
                    value,
                    "stocksell=" + name,
                    CurrencyType.USD
            );
            if (!r.success) {
                return r;
            }

            int currentPrice = s.getCurrentPrice();
            //reduce user's stock amount, and add realized profit
            for (int i = 0; i < stockList.size(); i++) {
                Stock one = stockList.get(i);
                int buyPrice = one.getBuyInPrice();
                if (one.getAmount() <= amount) {
                    one.addRealizedProfit(one.getAmount() * (currentPrice - buyPrice));
                    amount -= one.getAmount();
                    one.setAmount(0);
                    Stock.dao.update(one);
                } else {
                    one.addRealizedProfit(amount * (currentPrice - buyPrice));
                    one.setAmount(one.getAmount() - amount);
                    Stock.dao.update(one);
                }
            }
        } catch (SQLException e) {
            Logger.error("SQL Exception in sellStock:" + e + e.getMessage());
            return new Result<>(false, "sellStock: " + e + e.getMessage(), null);
        }
        return new Result<>(true, "sellStock: success ", null);
    }

    public Result<Integer> getRealizedProfit(String name, User user) {
        try {
            List<Stock> stockList = Stock.dao.queryBuilder().where().eq("name", name)
                    .and().eq("userId", user.getId()).query();
            int realizedProfit = 0;
            for (Stock stock : stockList) {
                realizedProfit += stock.getRealizedProfit();
            }
            return new Result<>(true, "getRealizedProfit: success ", realizedProfit);
        } catch (SQLException e) {
            Logger.error("SQL Exception in getRealizedProfit:" + e + e.getMessage());
            return new Result<>(false, "getRealizedProfit: " + e + e.getMessage(), null);
        }
    }

    public Result<Integer> getUnrealizedProfit(String name, User user) {
        try {
            List<Stock> stockList = Stock.dao.queryBuilder().where().eq("name", name)
                    .and().eq("userId", user.getId()).query();
            int currentPrice = Stock.dao.queryBuilder().where().eq("name", name)
                    .and().eq("userId", STOCK_MANAGER_USER_ID).queryForFirst().getCurrentPrice();
            int unrealizedProfit = 0;
            for (Stock stock : stockList
            ) {
                int buyPrice = stock.getBuyInPrice();
                int amount = stock.getAmount();
                unrealizedProfit += (currentPrice - buyPrice) * amount;
            }
            return new Result<>(true, "getUnrealizedProfit: success ", unrealizedProfit);
        } catch (SQLException e) {
            Logger.error("SQL Exception in getUnrealizedProfit:" + e + e.getMessage());
            return new Result<>(false, "getUnrealizedProfit: " + e + e.getMessage(), null);
        }

    }
}
