package bankBackend.entity;

import bankBackend.dao.DaoManager;
import bankBackend.entity.enums.CurrencyType;
import bankBackend.service.SvcMgr;
import bankBackend.entity.enums.TransactionType;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "Transactions")
public class Transaction {
    public static Dao<Transaction, Integer> dao = DaoManager.getDao(Transaction.class);
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    public int fromBalanceId;
    @DatabaseField
    public int toAccountId;
    @DatabaseField
    public TransactionType type;
    @DatabaseField
    public int value;
    @DatabaseField
    public int epoch;
    @DatabaseField
    public String description;
    @DatabaseField
    public CurrencyType currencyType;


    public Transaction() {
        // ORMLite needs a no-arg constructor
    }

    public int getId() {
        return id;
    }

    private Transaction(int fromBalanceId, int toAccountId, TransactionType type, int value) {
        this.fromBalanceId = fromBalanceId;
        this.toAccountId = toAccountId;
        this.type = type;
        this.value = value;
        this.epoch = SvcMgr.getDateTimeService().getCurrentEpoch();
    }

    public Transaction(int fromBalanceId, int toAccountId, TransactionType type, int value, String description, CurrencyType ct) {
        this(fromBalanceId, toAccountId, type, value);
        this.description = description;
        this.currencyType = ct;
    }

    public String toString() {
        return String.format("Tx[from=%d to=%d, type=%s, value=%s, epoch=%d, description=%s currencyType=%s]", fromBalanceId, toAccountId, type, value, epoch, description, currencyType);
    }

}
