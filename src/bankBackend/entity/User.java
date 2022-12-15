package bankBackend.entity;

import Utils.*;
import bankBackend.Config;
import bankBackend.dao.DaoManager;
import bankBackend.entity.account.*;
import bankBackend.entity.enums.AccountState;
import bankBackend.entity.enums.AccountType;
import bankBackend.service.SvcMgr;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@DatabaseTable(tableName = "Users")
public class User {
    public static Dao<User, Integer> dao = DaoManager.getDao(User.class);

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(unique = true)
    private String name;
    @DatabaseField
    private String password;
    @DatabaseField
    private boolean isManager;

    public User() {
        // ORMLite needs a no-arg constructor
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public static Result<User> getUserById(int id) {
        try {
            User u = dao.queryForId(id);
            if (u == null) return new Result<>(false, "no such user", null);
            return new Result<>(u);
        } catch (SQLException e) {
            return new Result<>(false, e.getMessage(), null);
        }
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
        try {
            dao.update(this);
        } catch (SQLException e) {
            Logger.fatal("Failed to update user:" + e.getMessage());
        }
    }

    public boolean isManager() {
        return isManager;
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

    public Result<Account> getAccount(AccountType type) {
        try {
            List<Account> accounts = Account.dao.queryBuilder().where().eq("userId", this.id).and().eq("type", type).query();
            if (accounts.size() == 0) {
                Logger.warn("Account not found, this should not happen");
                return new Result<>(false, "Account not found", null);
            }
            return new Result<>(true, "Account found", accounts.get(0));
        } catch (SQLException e) {
            Logger.error("getAccount:" + e.getMessage());
        }
        return new Result<>(false, "Account not found", null);
    }

    public Result<Account> getSecurityAccount() {
        if (!isSecurityAccountEnabled()) {
            return new Result(false, "Security account is not enabled", null);
        }

        Result r = getAccount(AccountType.Security);
        return r;
    }

    public Result<Account> getLoanAccount() {
        // ugly hack
        Result r = getAccount(AccountType.Loan);
        return r;
    }

    public Result<Account> getSavingAccount() {
        Result r = this.getAccount(AccountType.SAVINGS);
        return r;
    }

    public Result<Account> getCheckingAccount() {
        return this.getAccount(AccountType.CHECKING);
    }

    public boolean isSecurityAccountEnabled() {
        Result<Account> res = getSavingAccount();

        if (!res.success) {
            return false;
        }
        Account saving = res.data;

        for (Balance b : SvcMgr.getAccountService().listBalance(saving.getId())) {
            if (b.getValue() > Config.SECURITY_ACC_OPEN_THRESHOLD) {
                return true;
            }
        }
        return false;
    }

    public boolean isLoanAccountEnabled() {
        return false;
    }

    public List<Account> listAccount(AccountState state) {
        List<Account> accs = new ArrayList<>();
        Result r = getCheckingAccount();

        if (r.success && ((Account) r.data).getState().equals(state)) accs.add((Account) r.data);
        r = getSavingAccount();
        if (r.success && ((Account) r.data).getState().equals(state)) accs.add((Account) r.data);
        r = getLoanAccount();
        if (r.success && ((Account) r.data).getState().equals(state)) accs.add((Account) r.data);
        r = getSecurityAccount();
        if (r.success && ((Account) r.data).getState().equals(state)) accs.add((Account) r.data);
        return accs;
    }

    public Result<Report> getReport(int date) {
        return Report.getReport(this, date);
    }
}
