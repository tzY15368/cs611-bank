package bankBackend;

import Utils.Result;

import java.util.List;

public interface IUser {
    List<Account> listAccount();

    boolean isSecurityAccountEnabled();

    Result<CheckingAccount> getCheckingAccount();

    Result<SavingAccount> getSavingAccount();

    Result<LoanAccount> getLoanAccount();

    Result<SecurityAccount> getSecurityAccount();

    Result<Void> setPassword(String password);

    Result<Void> setName(String name);

    int getId();

    String getName();
}
