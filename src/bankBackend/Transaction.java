package bankBackend;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    CHARGEFEE
}

@DatabaseTable(tableName = "Transactions")
public class Transaction {
    @DatabaseField
    public int fromBalanceId;
    @DatabaseField
    public int toAccountId;
    @DatabaseField
    public TransactionType type;
    @DatabaseField
    public int value;


    public Transaction() {
    }

    public Transaction(int fromBalanceId, int toAccountId, TransactionType type, int value) {
        this.fromBalanceId = fromBalanceId;
        this.toAccountId = toAccountId;
        this.type = type;
        this.value = value;
    }
}
