package bankBackend.factory;

import Utils.Logger;
import Utils.Result;
import bankBackend.Config;
import bankBackend.entity.User;
import bankBackend.entity.account.Account;
import bankBackend.entity.enums.AccountState;
import bankBackend.service.impl.AccountCtl;
import bankBackend.service.impl.UserCtl;

import java.sql.SQLException;

public class ManagerFactory extends AbstractUserFactory {
    @Override
    public Result<Void> createUser(String name, String password) {
        // create a normal user first
        Result r1 = new DefaultUserFactory().createUser(name, password);
        UserCtl.getInstance().getManager(name).setIsManager(true);
        // open user's all factories
        User mgr = UserCtl.getInstance().getManager(name);
        // directly update the dao of account
        try {
            Account.dao.updateRaw("update accounts set state = ? where userId = ?", AccountState.OPEN.toString(), "" + mgr.getId());
        } catch (SQLException throwables) {
            Logger.fatal("Failed to open all accounts of manager:" + throwables.getMessage());
        }
        return r1;
    }
}
