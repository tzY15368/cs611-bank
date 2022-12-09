package bankBackend.entity;

import Utils.Result;
import bankBackend.Constants;
import bankBackend.dao.DaoManager;
import bankBackend.entity.account.Account;
import bankBackend.service.SvcMgr;
import bankBackend.entity.enums.TransactionType;
import bankBackend.service.impl.UserCtl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.HashMap;
import java.util.Map;


@DatabaseTable(tableName = "Transactions")
public class Transaction {
    public static Dao<Transaction, Integer> dao = DaoManager.getDao(Transaction.class);

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
        // ORMLite needs a no-arg constructor
    }

    public int getDate() {
        return date;
    }

    private Transaction(int fromBalanceId, int toAccountId, TransactionType type, int value) {
        this.fromBalanceId = fromBalanceId;
        this.toAccountId = toAccountId;
        this.type = type;
        this.value = value;
        this.date = SvcMgr.getDateTimeService().getCurrentDate().getDate();
    }

    // if type==CHARGE_FEE, toAccountId is ignored
    public static Result<Transaction> makeTransaction(int fromBalanceId, int toAccountId, TransactionType type, int value) {
        Transaction tx = new Transaction(fromBalanceId, toAccountId, type, value);

        int TX_TRANSFER_FEE_VALUE = 5;
        int DEPOSIT_FEE_VALUE = 5;
        int WITHDRAW_FEE_VALUE = 5;
        Map<TransactionType, Integer> chargeFeeValues = new HashMap<>() {
            {
                put(TransactionType.TRANSFER, TX_TRANSFER_FEE_VALUE);
                put(TransactionType.DEPOSIT, DEPOSIT_FEE_VALUE);
                put(TransactionType.WITHDRAW, WITHDRAW_FEE_VALUE);
            }
        };

        if (type != TransactionType.CHARGE_FEE && type != TransactionType.INTEREST) {
            // make the charge-fee transaction first
            int chargeFee = chargeFeeValues.get(type);
            if (value < chargeFee) {
                return new Result<>(false, "Transaction value is too small", null);
            }
            Transaction chargeFeeTx = makeTransaction(fromBalanceId, Constants.BANK_MANAGER_USER_ID, TransactionType.CHARGE_FEE, chargeFee).data;
            int srcAccountId = Balance.getBalanceById(fromBalanceId).getAccountId();
            Result r = Account.getAccountById(srcAccountId).handleTransaction(chargeFeeTx);
            if (!r.success) {
                return new Result<>(false, "Failed to handle charge-fee transaction:" + r.msg, null);
            }
        }
        return new Result<>(tx);
    }
}
