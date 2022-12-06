package bankBackend;

import Utils.Logger;
import Utils.Result;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Report {
    private int date;
    private List<Transaction> transactionList;

    public Report(int date){
        this.date=date;
        this.transactionList=setTransaction(date).getData();
    }

    private Result<List<Transaction>> setTransaction(int date){
        try {
            List<Transaction> transactionList=Transaction.dao.queryBuilder().where().eq("date", date).query();
            return new Result<>(true, "getTransaction: success", transactionList);
        }catch (SQLException e) {
            Logger.error("SQL Exception in getTransaction:"+e+e.getMessage());
            return new Result<>(false, "getTransaction: " + e.getMessage(), null);
        }
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }
}
