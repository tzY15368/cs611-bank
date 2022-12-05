package bankBackend;

import Utils.Logger;
import Utils.Result;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockCtl{

    public static Result<List<Stock>> listStocks() {
        try {
            List<Stock> stockArrayList=Stock.dao.queryForAll();
            return new Result<>(true, "listStock: success " , stockArrayList);
        } catch (SQLException e) {
            Logger.error("SQL Exception in listStock:"+e+e.getMessage());
            return new Result<>(false, "listStocks: " + e.getMessage(), null);
        }
    }

    public static Result<Void> addStock(Stock stock) {
        try {
            Stock s = Stock.dao.queryBuilder().where().eq("name", stock.getName()).queryForFirst();
            if(s!=null){
                return new Result<>(false, "stock already exists " , null);
            }
            Stock.dao.createIfNotExists(stock);
        } catch (SQLException e) {
            Logger.error("SQL Exception in addStock:"+e+e.getMessage());
            return new Result<>(false, "addStock: " + e.getMessage(), null);
        }
        return new Result<>(true, "addStock: success ", null);
    }

    public static Result<Void> removeStock(String stockName) {
        try {
            Stock stock = Stock.dao.queryBuilder().where().eq("name", stockName).and().eq("userId", -1).queryForFirst();
            if(stock==null){
                return new Result<>(false, "Stock doesn't exist", null);
            }
            Stock.dao.delete(stock);
        } catch (SQLException e) {
            Logger.error("SQL Exception in removeStock:"+e+e.getMessage());
            return new Result<>(false, "removeStock: "+ e + ": " + e.getMessage(), null);
        }
        return new Result<>(true, "removeStock: success ", null);
    }

    public static Result<Void> updateStock(Stock stock) {
        try {
            Stock s = Stock.dao.queryBuilder().where().eq("name", stock.getName()).and().eq("userId", -1).queryForFirst();
            if(s==null){
                return new Result<>(false, "Stock doesn't exist", null);
            }
            Stock.dao.delete(s);
            Stock.dao.create(stock);
        }catch (SQLException e){
            Logger.error("SQL Exception in updateStock:"+e+e.getMessage());
            return new Result<>(false, "addStock: " + e.getMessage(), null);
        }
        return new Result<>(true, "addStock: success ", null);
    }

    public static Result<Void> buyStock(String name, User user, int amount) {
        try {
            Stock stock = Stock.dao.queryBuilder().where().eq("name", name).and().eq("userId", -1).queryForFirst();
            //check if user can buy stock
            if (stock == null) {
                return new Result<>(false, "Stock doesn't exist", null);
            }
            if(stock.getAmount()<amount){
                return new Result<>(false, "Stock's amount is not enough", null);
            }

            // assume we can only use USD to buy stock, reset user's money
            int needMoney= amount*stock.getCurrentPrice();
            Balance balance=Balance.getBalanceWithCurrency(Account.getAccount(user.getId(), AccountType.Security),CurrencyType.USD).getData();
            if(balance.getValue()<needMoney){
                return new Result<>(false, "Don't have enough money to buy stock", null);
            }
            else{
                balance.deltaValue(0-needMoney);
                Balance.dao.update(balance);
            }

            // reset the amount of stock in the market
            stock.setAmount(stock.getAmount()-amount);
            Stock.dao.update(stock);

            // reset the amount of stock of user
            stock.setAmount(amount);
            stock.setUserId(user.getId());
            stock.setBuyInPrice(stock.getCurrentPrice());

            Stock s = Stock.dao.queryBuilder().where().eq("name", name).and().eq("userId", user.getId()).queryForFirst();
            if (s == null) {
                // TODO:not sure if generate new id
                Stock.dao.create(stock);
                return new Result<>(true, "buyStock: success: " , null);
            }
            s.setBuyInPrice(stock.getCurrentPrice());
            s.setAmount(amount+ s.getAmount());
            Stock.dao.update(s);
        }catch (SQLException e){
            Logger.error("SQL Exception in buyStock:"+e+e.getMessage());
            return new Result<>(false, "buyStock: "+e+e.getMessage(), null);
        }
        return new Result<>(true, "buyStock: success ", null);

    }

    public static Result<Void> sellStock(String name, User user, int amount) {
        try {
            Stock stock=Stock.dao.queryBuilder().where().eq("name", name).and().eq("userId", user.getId()).queryForFirst();
            //check if user can sell stock
            if(stock==null){
                return new Result<>(false, "Stock doesn't exist", null);
            }
            if(stock.getAmount()<amount){
                return new Result<>(false, "Doesn't have enough amount to sell", null);
            }

            //give user sell stock money
            Stock s=Stock.dao.queryBuilder().where().eq("name", name).and().eq("userId", -1).queryForFirst();
            int sellMoney=s.getCurrentPrice()*amount;
            Balance balance=Balance.getBalanceWithCurrency(Account.getAccount(user.getId(), AccountType.Security),CurrencyType.USD).getData();
            balance.deltaValue(sellMoney);
            Balance.dao.update(balance);

            // add money to realized profit
            int currentPrice=s.getCurrentPrice();
            int buyPrice=stock.getBuyInPrice();
            stock.addRealizedProfit(amount*(currentPrice-buyPrice));

            // reduce the amount of stock user have
            stock.setAmount(stock.getAmount()-amount);
            Stock.dao.update(stock);

        } catch (SQLException e){
            Logger.error("SQL Exception in sellStock:"+e+e.getMessage());
            return new Result<>(false, "sellStock: "+e+e.getMessage(), null);
        }
        return new Result<>(true, "sellStock: success ", null);
    }

    public static Result<Integer> getRealizedProfit(String name, User user) {
        try {
            int realizedProfit = Stock.dao.queryBuilder().where().eq("name", name).and().eq("userId", user.getId()).queryForFirst().getRealizedProfit();
            return new Result<>(true, "getRealizedProfit: success ", realizedProfit);
        }catch (SQLException e){
            Logger.error("SQL Exception in getRealizedProfit:"+e+e.getMessage());
            return new Result<>(false, "getRealizedProfit: "+e+e.getMessage(), null);
        }
    }

    public static Result<Integer> getUnrealizedProfit(String name, User user) {
        try{
            Stock stock=Stock.dao.queryBuilder().where().eq("name", name).and().eq("userId", user.getId()).queryForFirst();
            int buyPrice= stock.getBuyInPrice();
            int amount= stock.getAmount();
            int currentPrice=Stock.dao.queryBuilder().where().eq("name", name).and().eq("userId", -1).queryForFirst().getCurrentPrice();
            int unrealizedProfit = (currentPrice-buyPrice)*amount;
            return new Result<>(true, "getUnrealizedProfit: success ", unrealizedProfit);
        }catch (SQLException e){
            Logger.error("SQL Exception in getUnrealizedProfit:"+e+e.getMessage());
            return new Result<>(false, "getUnrealizedProfit: "+e+e.getMessage(), null);
        }
    }
}
