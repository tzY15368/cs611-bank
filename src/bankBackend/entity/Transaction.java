package bankBackend.entity;

import Utils.Result;
import bankBackend.Constants;
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

    public static Result<Transaction> makeTransaction(int fromBalanceId, int toAccountId, TransactionType type, int value, String description, CurrencyType ct) {
        Result<Transaction> rtx = makeTransaction(fromBalanceId, toAccountId, type, value, description);
        rtx.unwrap().currencyType = ct;
        return rtx;
    }

    // if type==CHARGE_FEE, toAccountId is ignored
    public static Result<Transaction> makeTransaction(int fromBalanceId, int toAccountId, TransactionType type, int value, String description) {
        Transaction tx = new Transaction(fromBalanceId, toAccountId, type, value);
        tx.description = description;


        if (type != TransactionType.CHARGE_FEE && type != TransactionType.INTEREST && type != TransactionType.DEPOSIT) {
            // make the charge-fee transaction first
            int chargeFee = Constants.ALL_CHARGE_FEE_VALUES.get(type);
            if (value < chargeFee) {
                return new Result<>(false, "Transaction value is too small", null);
            }
            Transaction chargeFeeTx = makeTransaction(
                    fromBalanceId,
                    Constants.BANK_MANAGER_USER_ID,
                    TransactionType.CHARGE_FEE,
                    chargeFee,
                    "Operation fee"
            ).unwrap();
            Result r = SvcMgr.getAccountService().handleTxn(chargeFeeTx);
            if (!r.success) {
                return new Result<>(false, "Failed to handle charge-fee transaction:" + r.msg, null);
            }
        }
        return new Result<>(tx);
    }
}
