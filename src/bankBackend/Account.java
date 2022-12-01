package bankBackend;

import Utils.Result;
import com.j256.ormlite.field.DatabaseField;

import java.util.ArrayList;

public abstract class Account {
    @DatabaseField
    private int id;
    @DatabaseField
    private int userId;

    public Account(){}

    public Account(int id, int userId){
        this.id=id;
        this.userId=userId;
    }

    private ArrayList<Balance> balanceArrayList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Result<Void> addBalance(Balance balance) {
        this.balanceArrayList.add(balance);
        return null;
    }

    public Result<Void> deleteBalance(Balance balance){
        this.balanceArrayList.remove(balance);
        return null;
    }

    public Result<Void> openAccount(){
        return null;
    }

    public Result<Void> closeAccount(){
        return null;
    }
}
