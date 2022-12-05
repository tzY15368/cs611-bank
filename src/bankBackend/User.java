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

    public static Result<Void> userLogin(String username, String password) {
        try {
            User user = dao.queryBuilder().where().eq("name", username).queryForFirst();
            if (user == null) {
                return new Result<>(false, "User not found", null);
            }
            if (user.getPassword().equals(password)) {
                // set session
                SessionMgr.setSession(new BasicSession(user));

                return new Result<>(true, null, null);
            } else {
                return new Result<>(false, "Wrong password", null);
            }
        } catch (SQLException e) {
            return new Result<>(false, "SQL Exception in userLogin:" + e + ": " + e.getMessage(), null);
        }
    }

    public static Result<Void> userRegister(String username, String password) {
        try {
            User user = dao.queryBuilder().where().eq("name", username).queryForFirst();
            if (user != null) {
                return new Result<>(false, "User already exists", null);
            }
            User newUser = new User(username, password);
            dao.create(newUser);
            Account[] accs = new Account[]{
                    new CheckingAccount(newUser.getId()),
                    new SavingAccount(newUser.getId()),
                    new LoanAccount(newUser.getId()),
                    new SecurityAccount(newUser.getId())
            };
            List.of(accs).forEach(acc -> {
                try {
                    Account.dao.create(acc);
                } catch (SQLException e) {
                    Logger.fatal("SQL Exception in userRegister:" + e + ": " + e.getMessage());
                }
            });

            return new Result<>();
        } catch (SQLException e) {
            return new Result<>(false, "SQL Exception in userRegister:" + e + ": " + e.getMessage(), null);
        }
    }
    public static Result<Report> getResult(int userId) {
        return new Result<>(true, null, null);
    }
}
