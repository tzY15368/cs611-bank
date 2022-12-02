package bankBackend;

import Utils.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manager extends User {
    public static List<User> listUsers() {
        try {
            return User.dao.queryForAll();
        } catch (Exception e) {
            Logger.error("listUsers:" + e.getMessage());
        }
        return new ArrayList<>();
    }
}
