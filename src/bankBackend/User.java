package bankBackend;

import Utils.DBManager;
import Utils.Result;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;


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
                return new Result<Void>(false, "User not found", null);
            }
            if (user.getPassword().equals(password)) {
                return new Result<Void>(true, null, null);
            } else {
                return new Result<Void>(false, "Wrong password", null);
            }
        } catch (SQLException e) {
            return new Result<Void>(false, "SQL Exception in userLogin:" + e + ": " + e.getMessage(), null);
        }
    }

    ;

    public static Result<Void> userRegister(String username, String password) {
        try {
            User user = dao.queryBuilder().where().eq("name", username).queryForFirst();
            if (user != null) {
                return new Result<Void>(false, "User already exists", null);
            }
            User newUser = new User(username, password);
            dao.create(newUser);
            return new Result<Void>(true, null, null);
        } catch (SQLException e) {
            return new Result<Void>(false, "SQL Exception in userRegister:" + e + ": " + e.getMessage(), null);
        }
    }

    ;
}
