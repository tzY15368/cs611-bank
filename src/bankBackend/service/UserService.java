package bankBackend.service;

import Utils.Result;
import bankBackend.entity.User;
import bankBackend.entity.account.Account;
import bankBackend.entity.enums.AccountState;

import java.util.List;

public interface UserService {
    Result<User> userLogin(String username, String password);

    Result<Void> userRegister(String username, String password);

    List<User> listUsers();

    List<Account> listAccount(User user);

    List<Account> listAccount(User user, AccountState state);

    Result<User> getUserByName(String username);

    String getUsernameByAccountId(int accountId);

    Result<Void> updateUser(int id, String name, String password);
}
