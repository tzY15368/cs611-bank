package bankBackend.service.impl;

import Utils.*;
import bankBackend.Config;
import bankBackend.entity.account.Account;
import bankBackend.entity.enums.AccountState;
import bankBackend.entity.enums.AccountType;
import bankBackend.factory.AbstractUserFactory;
import bankBackend.factory.ManagerFactory;
import bankBackend.factory.MemorySession;
import bankBackend.factory.DefaultUserFactory;
import bankBackend.entity.User;
import bankBackend.service.SvcMgr;
import bankBackend.service.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserCtl implements UserService {

    private AbstractUserFactory userFactory;
    private static UserCtl instance = null;

    private UserCtl() {
        userFactory = new DefaultUserFactory();
    }

    public static void init() {
        if (instance == null) {
            instance = new UserCtl();
        }
        // create if not exists the bank manager
        Result r = new ManagerFactory().createUser("", "");
        if (!r.success) Logger.error("Failed to create bank manager:" + r.msg);
        //re-fetch the bank manager
        Config.BANK_MANAGER_USER_ID = instance.getManager().getId();
    }

    public User getManager() {
        try {
            return User.dao.queryBuilder().where()
                    .eq("name", Config.BANK_MANAGER_USERNAME).queryForFirst();
        } catch (SQLException e) {
            Logger.fatal("Failed to fetch bank manager:" + e.getMessage());
            return null;
        }
    }

    public static UserCtl getInstance() {
        if (instance == null) {
            instance = new UserCtl();
        }
        return instance;
    }

    public Result<Void> updateUser(int id, String name, String password) {
        try {
            User user = User.dao.queryForId(id);
            if (user == null) {
                return new Result<>(false, "User not found", null);
            }
            user.setName(name);
            user.setPassword(password);
            User.dao.update(user);
            return new Result<>();
        } catch (SQLException e) {
            return new Result(false, e.getMessage(), null);
        }
    }

    public Result<User> userLogin(String username, String password) {
        try {
            User user = User.dao.queryBuilder().where().eq("name", username).queryForFirst();
            if (user == null) {
                return new Result<>(false, "User not found", null);
            }
            if (user.getPassword().equals(password)) {
                // set session
                SvcMgr.getSessionService().setSession(new MemorySession(user));

                return new Result<>(true, null, user);
            } else {
                return new Result<>(false, "Wrong password", null);
            }
        } catch (SQLException e) {
            return new Result<>(false, "SQL Exception in userLogin:" + e + ": " + e.getMessage(), null);
        }
    }

    public Result<Void> userRegister(String username, String password) {
        return userFactory.createUser(username, password);
    }

    public List<User> listUsers() {
        try {
            return User.dao.queryForAll();
        } catch (Exception e) {
            Logger.error("listUsers:" + e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Account> listAccount(User user) {
        List<Account> accs = new ArrayList<>();
        List.of(AccountType.values()).forEach(t -> {
            try {
                accs.addAll(Account.dao.queryBuilder().where()
                        .eq("userId", user.getId())
                        .and().eq("type", t)
                        .query());
            } catch (SQLException e) {
                Logger.error("listAccount:" + e.getMessage());
            }
        });
        return accs;
    }

    @Override
    public List<Account> listAccount(User user, AccountState state) {
        List<Account> accs = new ArrayList<>();
        Result r = user.getCheckingAccount();

        if (r.success && ((Account) r.data).getState().equals(state)) accs.add((Account) r.data);
        r = user.getSavingAccount();
        if (r.success && ((Account) r.data).getState().equals(state)) accs.add((Account) r.data);
        r = user.getLoanAccount();
        if (r.success && ((Account) r.data).getState().equals(state)) accs.add((Account) r.data);
        r = user.getSecurityAccount();
        if (r.success && ((Account) r.data).getState().equals(state)) accs.add((Account) r.data);
        return accs;
    }

    @Override
    public Result<User> getUserByName(String username) {
        try {
            User user = User.dao.queryBuilder().where().eq("name", username).queryForFirst();
            if (user == null) {
                return new Result<>(false, "User not found", null);
            }
            return new Result<>(true, null, user);
        } catch (SQLException e) {
            return new Result<>(false, "SQL Exception in getUserByName:" + e + ": " + e.getMessage(), null);
        }
    }

    @Override
    public String getUsernameByAccountId(int accountId) {
        try {
            int uid = SvcMgr.getAccountService().getAccountById(accountId).getUserId();
            return User.dao.queryBuilder().where().eq("id", uid).queryForFirst().getName();
        } catch (SQLException e) {
            Logger.error("getUsernameByAccountId:" + e.getMessage());
            return "unknown";
        }
    }
}
