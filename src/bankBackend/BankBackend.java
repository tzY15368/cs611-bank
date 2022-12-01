package bankBackend;


import Utils.*;

import java.util.Arrays;


public class BankBackend {
    public String name;

    public BankBackend() {
        this.name = "helo";

        Result r = DBManager.init();
        if (!r.isSuccess()) {
            Logger.fatal(r.getMsg());
        }

        // get a default user for the session
        User usr2 = null;

        try {
            User.dao.createIfNotExists(new User("npm","password123"));
            usr2 = User.dao.queryForEq("name", "npm").get(0);
            Logger.info("usr-session:" + usr2.getName());
        } catch (Exception e) {
            Logger.fatal(e.getMessage());
        }
        SessionMgr.setSession(new BasicSession(usr2));

        // test the session

        User usr = SessionMgr.getSession().getData().getUser();
        Logger.info("usr-got:" + usr.getName());
    }
}
