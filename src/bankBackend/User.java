package bankBackend;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;




@DatabaseTable(tableName = "Users")
public class User {

    @DatabaseField(id = true)
    private String name;
    @DatabaseField
    private String password;

    public User() {
        // ORMLite needs a no-arg constructor
    }
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
