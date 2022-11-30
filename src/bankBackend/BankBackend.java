package bankBackend;


import Utils.DBManager;
import Utils.Logger;

import java.util.Arrays;


public class BankBackend {
    public String name;

    public BankBackend() {
        this.name = "helo";
        try {
            DBManager.init();
            User usr = new User();
            usr.setName("npm");
            User.dao.createIfNotExists(usr);

            User usr2 = User.dao.queryForEq("name", "npm").get(0);
            Logger.info("usr:" + usr2.getName());
            DBManager.close();

        } catch (Exception e) {
            Logger.error("Exception in init:" + e + ": " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }
}
