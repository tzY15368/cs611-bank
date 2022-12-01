package bankBackend;

import Utils.Result;

import java.util.ArrayList;

public class SavingAccount extends Account{
    public SavingAccount(int id, int userId){
        super(id,userId);
    }
    public Result<Boolean> validForStock(){
        return new Result(true,"",true);
    }
}
