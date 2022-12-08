package bankBackend;

import Utils.Logger;
import Utils.Result;

import java.sql.SQLException;
import java.util.List;

public class DefaultUserFactory extends AbstractUserFactory {
    @Override
    public Result<Void> createUser(String username, String password) {
        try {
            User user = User.dao.queryBuilder().where().eq("name", username).queryForFirst();
            if (user != null) {
                return new Result<>(false, "User already exists", null);
            }
            User newUser = new User(username, password);
            User.dao.create(newUser);
            Account[] accs = new Account[]{
                    new CheckingAccount(newUser.getId()),
                    new SavingAccount(newUser.getId()),
                    new LoanAccount(newUser.getId()),
                    new SecurityAccount(newUser.getId())
            };

            List.of(accs).forEach(acc -> {
                try {
                    Account.dao.create(acc);
                    List.of(CurrencyType.values()).forEach(currencyType -> {
                        Result r = Balance.createBalance(acc, currencyType);
                        if(!r.success){
                            Logger.error("createUser: " + r.msg);
                        }
                    });
                } catch (SQLException e) {
                    Logger.fatal("SQL Exception in userRegister:" + e + ": " + e.getMessage());
                    try {
                        // FIXME: rollback, should be done in a transaction
                        User.dao.delete(newUser);
                    } catch (SQLException e1) {
                        Logger.fatal("SQL Exception in userRegister:" + e1 + ": " + e1.getMessage());
                    }
                }
            });

            return new Result<>();
        } catch (SQLException e) {
            return new Result<>(false, "SQL Exception in userRegister:" + e + ": " + e.getMessage(), null);
        }
    }
}

