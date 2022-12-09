package bankBackend.service.impl;

import Utils.*;
import bankBackend.factory.AbstractUserFactory;
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

    public Result<Report> getReport(int userId) {
        return Report.getReport(DateTimeCtl.getInstance().getCurrentDate().getDate());
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
