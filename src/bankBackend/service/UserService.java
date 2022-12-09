package bankBackend.service;

import Utils.Result;
import bankBackend.entity.User;

import java.util.List;

public interface UserService {
    Result<Void> userLogin(String username, String password);

    Result<Void> userRegister(String username, String password);

    List<User> listUsers();
}
