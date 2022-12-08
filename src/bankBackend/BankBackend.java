package bankBackend;

import Utils.*;
import com.j256.ormlite.logger.LocalLogBackend;

public class BankBackend {
    public String name;

    public BankBackend() {
        this.name = "helo";
        // turn off logging in ormlite
        System.setProperty(LocalLogBackend.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");
        Result r = DBManager.init();

        if (!r.success) {
            Logger.fatal(r.msg);
        }
        // note that this would start a new thread.
        Timer.init();

        // get a default user for the session
//        User usr2 = null;
//
//        try {
//            User.dao.createIfNotExists(new User("npm", "password123"));
//            usr2 = User.dao.queryForEq("name", "npm").get(0);
//            Logger.info("usr-session:" + usr2.getName());
//        } catch (Exception e) {
//            Logger.fatal(e.getMessage());
//        }
//        SessionMgr.setSession(new BasicSession(usr2));
        Result r2 = UserManager.getInstance().userRegister("hello","123");
        if(!r2.success){
            Logger.error(r2.msg);
        }
        r2 = UserManager.getInstance().userLogin("hello","123");
        if(!r2.success){
            Logger.fatal(r2.msg);
        }


        // test the session

        User usr = SessionMgr.getSession().data.getUser();
        Logger.info("usr-got:" + usr.getName());
    }
}
