package bankUI.StockUI;

import Utils.AlertUI;
import Utils.Logger;
import Utils.Result;
import bankBackend.StockCtl;
import bankBackend.User;

import javax.swing.*;

public class BuySellUI extends JPanel {
    private JTextField stockName;
    private JTextField amount;
    private JButton buyButton;
    private JButton sellButton;
    private User user;
    public BuySellUI(int userId){
        Result<User> userRes = User.getUserById(userId);
        if(!userRes.success||userRes.data.getId()<0){
            Logger.error("invalid userid "+userRes.msg);
            return;
        }
        this.user = userRes.data;
        stockName = new JTextField();
        amount = new JTextField();
        buyButton = new JButton("Buy");
        sellButton = new JButton("Sell");
        this.add(stockName);
        this.add(amount);
        this.add(buyButton);
        this.add(sellButton);

        buyButton.addActionListener(e -> {
            Result<Integer> actualAmount = this.getAmountInput();
            if(!actualAmount.success){
                Logger.warn(actualAmount.msg);
                AlertUI.error(actualAmount.msg);
                return;
            }
            Result res = StockCtl.buyStock(stockName.getText(),this.user,actualAmount.data);
            if(res.success){
                AlertUI.success("buy ok");
            } else {
                AlertUI.error("buy fail:"+res.msg);
            }
        });
        sellButton.addActionListener(e -> {
            Result<Integer> actualAmount = this.getAmountInput();
            if(!actualAmount.success){
                Logger.warn(actualAmount.msg);
                AlertUI.error(actualAmount.msg);
                return;
            }

            Result res = StockCtl.sellStock(stockName.getText(),this.user,actualAmount.data);
            if(res.success){
                AlertUI.success("buy ok");
            } else {
                AlertUI.error("buy fail:"+res.msg);
            }
        });
    }

    private Result<Integer> getAmountInput(){

        int actualAmount;
        try{
            actualAmount = Integer.parseInt(amount.getText());
            return new Result(actualAmount);
        } catch (Exception ee){
            String msg = "conversion error:"+ee.getMessage();
            return new Result(false,msg,0);
        }
    }
}
