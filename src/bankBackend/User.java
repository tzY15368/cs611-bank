package bankBackend;

import Utils.BasicSession;
import Utils.DBManager;
import Utils.Result;
import Utils.SessionMgr;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.List;


@DatabaseTable(tableName = "Users")
public class User {
    public static Dao<User, Integer> dao = DBManager.getDao(User.class);

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
        this.name = name;
        return null;
    }

    public String getPassword() {
        return password;
    }

    public Result<Void> setPassword(String password) {
        this.password = password;
        return null;
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
            return new Result<>(true, null, null);
        } catch (SQLException e) {
            return new Result<>(false, "SQL Exception in userRegister:" + e + ": " + e.getMessage(), null);
        }
    }

    public static Result<Report> getResult(int userId) {
        return new Result<>(true, null, null);
    }
}
