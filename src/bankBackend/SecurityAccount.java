package bankBackend;

import Utils.Result;

import java.util.ArrayList;
import java.util.HashMap;

public class SecurityAccount extends Account{
    private HashMap<Stock, Integer> stockArrayList;

    public HashMap<Stock, Integer> getStockArrayList() {
        return stockArrayList;
    }

    public SecurityAccount(int id, int userId){
        super(id,userId);
    }

    public Result<Void> buyStock(Stock stock){
        return null;
    }

    public Result<Void> sellStock(Stock stock){
        //get money and put into balanceArraylist in securityAccount
        return null;
    }
}
