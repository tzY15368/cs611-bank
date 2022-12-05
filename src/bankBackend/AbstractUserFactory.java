package bankBackend;

import Utils.Result;

abstract class AbstractUserFactory {
    public abstract Result<Void> createUser(String name, String password);
}
