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

    public static Result<Void> userLogin(String username, String password){
        return new Result<>();
    };

    public static Result<Void> userRegister(String username, String password){
        User u = new User();
        u.setName(username);
        return new Result<>(true,null,null);
    };
}
