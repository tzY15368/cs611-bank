package bankBackend.service.impl;

import Utils.*;
import bankBackend.Constants;
import bankBackend.factory.AbstractUserFactory;
import bankBackend.factory.ManagerFactory;
import bankBackend.factory.MemorySession;
import bankBackend.factory.DefaultUserFactory;
import bankBackend.entity.User;
import bankBackend.entity.Report;
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
        if (!r.success) Logger.fatal("Failed to create bank manager");
        //re-fetch the bank manager
        Constants.BANK_MANAGER_USER_ID = instance.getManager().getId();
    }

    public User getManager() {
        try {
            return User.dao.queryBuilder().where()
                    .eq("name", Constants.BANK_MANAGER_USERNAME).queryForFirst();
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

    public Result<Void> userLogin(String username, String password) {
        try {
            User user = User.dao.queryBuilder().where().eq("name", username).queryForFirst();
            if (user == null) {
                return new Result<>(false, "User not found", null);
            }
            if (user.getPassword().equals(password)) {
                // set session
                SvcMgr.getSessionService().setSession(new MemorySession(user));

                return new Result<>(true, null, null);
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
}
