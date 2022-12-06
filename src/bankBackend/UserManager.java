package bankBackend;

import Utils.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private AbstractUserFactory userFactory;
    private static UserManager instance = null;

    private UserManager() {
        userFactory = new DefaultUserFactory();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
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
                SessionMgr.setSession(new BasicSession(user));

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
        return Report.getReport(Timer.getInstance().getCurrentDate());
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
