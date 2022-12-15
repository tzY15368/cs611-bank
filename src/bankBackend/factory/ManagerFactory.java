package bankBackend.factory;

import Utils.Result;
import bankBackend.Config;
import bankBackend.service.impl.UserCtl;

public class ManagerFactory extends AbstractUserFactory {
    @Override
    public Result<Void> createUser(String name, String password) {
        // create a normal user first
        Result r1 = new DefaultUserFactory().createUser(Config.BANK_MANAGER_USERNAME, password);
        UserCtl.getInstance().getManager().setIsManager(true);
        return r1;
    }
}
