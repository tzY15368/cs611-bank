package bankBackend.service;

import Utils.Result;
import bankBackend.entity.Balance;
import bankBackend.entity.Transaction;
import bankBackend.entity.account.Account;
import bankBackend.entity.enums.AccountState;
import bankBackend.entity.enums.AccountType;

import java.util.List;

public interface AccountService {
    Account getAccountById(int accountId);

    // used by the generate interest callback
    List<Account> listAccountByType(AccountType type);

    Result<Void> handleTxn(Transaction tx);

    List<Balance> listBalance(int accountId);
}
