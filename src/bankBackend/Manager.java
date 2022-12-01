package bankBackend;

import Utils.Result;

import java.util.ArrayList;
import java.util.Date;

public class Manager extends User{
    private ArrayList<Stock> stockArrayList;

    public Report getReport(Date date){
        // date is generated upon request
        Report report=new Report();
        return report;
    }

    public Result<Void> chargeInterest(){
        return null;
    }

    public Result<Void> payInterest(){
        return null;
    }

    public ArrayList<Stock> addStock(String name, double price){
        Stock stock=new Stock(name,price);
        stockArrayList.add(stock);
        return stockArrayList;
    }

    public ArrayList<Stock> deleteStock(String name){
        return stockArrayList;
    }

    public Result<Void> changeStockPrice(String name){
        return null;
    }



}
