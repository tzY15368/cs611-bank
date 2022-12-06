package bankBackend;

import Utils.Logger;
import Utils.Result;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Report {
    private int date;
    private List<Transaction> transactionList;

    public Report(int date) {
        this.date = date;
        this.transactionList = new ArrayList<>();
    }

    public static Result<Report> getReport(int date) {
        Report r = new Report(date);
        Result<Void> res = r.setTransactions();
        if (!res.success) {
            return new Result<>(false, res.msg, null);
        }
        return new Result<>(r);
    }

    private Result<Void> setTransactions() {
        try {
            this.transactionList = Transaction.dao.queryBuilder().where().eq("date", date).query();
            return new Result<>();
        } catch (SQLException e) {
            Logger.error("SQL Exception in getTransaction:" + e + e.getMessage());
            return new Result<>(false, "getTransaction: " + e.getMessage(), null);
        }
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }
}
