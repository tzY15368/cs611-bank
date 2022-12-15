package bankUI.utils;

import bankBackend.entity.User;
import bankBackend.entity.account.Account;

import java.util.HashMap;
import java.util.Map;

public class UIContextMgr {
    private static Map<Object, User> userMap = new HashMap<>();
    private static Map<Object, Account> accountMap = new HashMap<>();

    public static void setUser(Object key, User user) {
        userMap.put(key, user);
    }

    public static User getUser(Object key) {
        return userMap.get(key);
    }

    public static void setAccount(Object key, Account account) {
        accountMap.put(key, account);
    }

    public static Account getAccount(Object key) {
        return accountMap.get(key);
    }
}
