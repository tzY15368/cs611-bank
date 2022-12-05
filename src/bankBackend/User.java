package bankBackend;

import Utils.*;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@DatabaseTable(tableName = "Users")
public class User {
    static Dao<User, Integer> dao = DBManager.getDao(User.class);
    static AbstractUserFactory userFactory = new DefaultUserFactory();
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String password;

    public User() {
        // ORMLite needs a no-arg constructor
    }

    public User(String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Result<Void> setName(String name) {
        // TODO: IMPLEMENT THIS
        return null;
    }

    public String getPassword() {
        return password;
    }

    public Result<Void> setPassword(String password) {
        this.password = password;
        return null;
    }

    public Result<SecurityAccount> getSecurityAccount() {
        if (!isSecurityAccountEnabled()) {
            return new Result(false, "Security account is not enabled", null);
        }
        return new Result(true, null, SecurityAccount.getAccount(id, AccountType.Security));
    }

    public Result<LoanAccount> getLoanAccount() {
        return new Result(true, null, LoanAccount.getAccount(id, AccountType.Loan));
    }

    public Result<SavingAccount> getSavingAccount() {
        return new Result(true, null, SavingAccount.getAccount(id, AccountType.SAVINGS));
    }

    public Result<CheckingAccount> getCheckingAccount() {
        return new Result(true, null, CheckingAccount.getAccount(id, AccountType.CHECKING));
    }

    public boolean isSecurityAccountEnabled() {
        Result<SavingAccount> res = getSavingAccount();
        if (!res.isSuccess()) {
            return false;
        }
        SavingAccount saving = res.getData();
        for (Balance b : saving.listBalance()) {
            if (b.getValue() > Constants.SECURITY_ACC_OPEN_THRESHOLD) {
                return true;
            }
        }
        return false;
    }

    public boolean isLoanAccountEnabled() {
        return false;
    }

    public List<Account> listAccount() {
        List<Account> accs = new ArrayList<>();
        Result r = getCheckingAccount();
        if (r.isSuccess()) accs.add((Account) r.getData());
        r = getSavingAccount();
        if (r.isSuccess()) accs.add((Account) r.getData());
        r = getLoanAccount();
        if (r.isSuccess()) accs.add((Account) r.getData());
        r = getSecurityAccount();
        if (r.isSuccess()) accs.add((Account) r.getData());
        return accs;
    }


}
