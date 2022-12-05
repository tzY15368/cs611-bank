package bankBackend;

import Utils.Result;

import java.util.HashMap;
import java.util.List;

public class SecurityAccount extends Account {
    private List<Stock> stockList;
    private List<Balance> balanceList;
    public SecurityAccount() {
        // ORMLite needs a no-arg constructor
        super(-1, AccountType.Security);
    }

    public SecurityAccount(int userId) {
        super(userId, AccountType.Security);
    }

}
