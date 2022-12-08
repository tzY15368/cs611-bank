package bankUI.StockUI;

import Utils.Constants;
import Utils.Logger;

import javax.swing.*;

public class StockHomeUI extends JPanel {
    private StockList marketList;
    private StockList myStockList;
    private BuySellUI buySell;

    // TODO: ADD STOCK CHANGE EVENT LISTENER

    public StockHomeUI(int userId){
        marketList = new StockList(Constants.STOCK_MANGAER_USER_ID, new String[]{"name", "currentPrice", "amount"});
        myStockList = new StockList(userId, new String[]{"name", "currentPrice", "amount"});
        buySell = new BuySellUI(userId);
        this.add(marketList);
        this.add(myStockList);
        this.add(buySell);
        Logger.info("StockHomeUI: init success");
    }
}
