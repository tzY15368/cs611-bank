package bankBackend;

import com.j256.ormlite.field.DatabaseField;

public abstract class Account {
    @DatabaseField
    private int id;
    @DatabaseField
    private int userId;
}
