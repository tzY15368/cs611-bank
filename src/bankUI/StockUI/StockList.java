package bankUI.StockUI;

import Utils.Logger;
import bankBackend.entity.Stock;
import bankBackend.service.impl.StockCtl;

import javax.swing.*;
import java.util.List;

public class StockList extends JPanel {

    private JList stocks;
    private DefaultListModel listModel;
    private String[] showFields;

    public StockList(int userId, String[] fields) {
        showFields = fields;
        List<Stock> stockList = StockCtl.listStocks(userId);
        listModel = new DefaultListModel();
        stockList.forEach(stock -> {
            listModel.addElement(String.format("%s %d %d", stock.getName(), stock.getCurrentPrice(), stock.getAmount()));
        });
        this.stocks = new JList(listModel);
        this.stocks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.stocks.setSelectedIndex(0);
        this.stocks.setVisible(true);
        this.add(stocks);
        Logger.info("StockList: init success with length " + stockList.size());
    }
}
