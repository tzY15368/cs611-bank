package bankBackend;

import Utils.*;
import bankBackend.entity.User;
import bankBackend.service.SvcMgr;
import com.j256.ormlite.logger.LocalLogBackend;

public class BankBackend {
    public String name;

    public BankBackend() {
        this.name = "helo";
        // turn off logging in ormlite
        System.setProperty(LocalLogBackend.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");

        // init services
        SvcMgr.init();

        // mock login
        Result r2 = SvcMgr.getUserService().userRegister("hello", "123");
        if (!r2.success) {
            Logger.error(r2.msg);
        }
        r2 = SvcMgr.getUserService().userLogin("hello", "123");
        if (!r2.success) {
            Logger.fatal(r2.msg);
        }

        // test the session
        User usr = SvcMgr.getSessionService().getSession().data.getUser();
        Logger.info("usr-got:" + usr.getName());
    }
}
