package bankBackend.entity;

import bankBackend.dao.DaoManager;
import bankBackend.service.SvcMgr;
import bankBackend.entity.enums.TransactionType;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "Transactions")
public class Transaction {
    static Dao<Transaction, Integer> dao = DaoManager.getDao(Transaction.class);

    @DatabaseField
    public int fromBalanceId;
    @DatabaseField
    public int toAccountId;
    @DatabaseField
    public TransactionType type;
    @DatabaseField
    public int value;
    @DatabaseField
    public int date;


    public Transaction() {
    }

    public int getDate() {
        return date;
    }

    public Transaction(int fromBalanceId, int toAccountId, TransactionType type, int value) {
        this.fromBalanceId = fromBalanceId;
        this.toAccountId = toAccountId;
        this.type = type;
        this.value = value;
        this.date = SvcMgr.getDateTimeService().getCurrentDate().getDate();
    }

}
