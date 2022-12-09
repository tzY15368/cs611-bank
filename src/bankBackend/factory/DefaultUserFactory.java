package bankBackend.factory;

import Utils.Logger;
import Utils.Result;
import bankBackend.entity.Balance;
import bankBackend.entity.InterestRate;
import bankBackend.entity.User;
import bankBackend.entity.account.*;
import bankBackend.entity.enums.CurrencyType;
import bankBackend.entity.enums.RateType;

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
            for (Account acc : accs) {
                Account.dao.create(acc);
                for (CurrencyType type : CurrencyType.values()) {
                    Result r = Balance.createBalance(acc, type);
                    if (!r.success) {
                        return r;
                    }
                }
            }
            // set default interest rates
            InterestRate saveir = new InterestRate(accs[1].getId(), RateType.Save);
            InterestRate loanir = new InterestRate(accs[2].getId(), RateType.Loan);
            InterestRate.dao.create(saveir);
            InterestRate.dao.create(loanir);

            return new Result<>();
        } catch (SQLException e) {
            return new Result<>(false, "SQL Exception in userRegister:" + e + ": " + e.getMessage(), null);
        }
    }
}

