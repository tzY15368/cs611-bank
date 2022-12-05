package bankBackend;

import Utils.Result;

import java.util.List;

public interface IUserManager {
    Result<Void> userLogin(String username, String password);

    Result<Void> userRegister(String username, String password);

    Result<Report> getReport(int userId);

    List<User> listUsers();
}
