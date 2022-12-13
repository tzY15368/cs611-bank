package bankBackend.entity.account;

import bankBackend.Constants;
import bankBackend.entity.enums.AccountState;
import bankBackend.entity.Balance;
import bankBackend.dao.DaoManager;
import Utils.Logger;
import Utils.Result;
import bankBackend.entity.Transaction;
import bankBackend.entity.enums.AccountType;
import bankBackend.entity.enums.CurrencyType;
import bankBackend.entity.enums.TransactionType;
import bankBackend.service.SvcMgr;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;


@DatabaseTable(tableName = "Accounts")
public class Account {

    public static Dao<Account, Integer> dao = DaoManager.getDao(Account.class);

    @DatabaseField(generatedId = true)
    protected int id;
    @DatabaseField
    protected int userId;

    @DatabaseField
    protected AccountType type;

    @DatabaseField
    protected AccountState state;

    public Account(int userId, AccountType type) {
        this.userId = userId;
        this.type = type;
        this.state = AccountState.INACTIVE;
    }

    public Account() {
        // ORMLite needs a no-arg constructor
    }

    public int getId() {
        return id;
    }

    public AccountState getState() {
        return state;
    }

    public Result<Void> setState(AccountState state) {
        this.state = state;
        try {
            dao.update(this);
            Balance thisUSDBalance = Balance.getBalanceWithCurrency(this.getId(), CurrencyType.USD).data;
            Result<Transaction> txRes = Transaction.makeTransaction(
                    thisUSDBalance.getId(),
                    0,
                    TransactionType.CHARGE_FEE,
                    Constants.TX_CHARGE_FEE_VALUE,
                    "Account state change fee"
            );
            if (!txRes.success) {
                return new Result(false, "Failed to charge fee:" + txRes.msg, null);
            }
            Result r = SvcMgr.getAccountService().handleTxn(txRes.data);
            return r;
        } catch (SQLException e) {
            return new Result<>(false, "SQL Exception in setState:" + e + ": " + e.getMessage(), null);
        }
    }

}
