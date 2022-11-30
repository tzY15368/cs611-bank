package bankBackend;


import Utils.Logger;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.Arrays;


public class BankBackend {
    public String name;

    public BankBackend(){
        this.name = "helo";
        try {
            ConnectionSource conn = new JdbcConnectionSource("jdbc:sqlite:bank.db");
            Dao<User, String> userDao = DaoManager.createDao(conn, User.class);
            TableUtils.createTableIfNotExists(conn, User.class);
            User usr = new User();
            usr.setName("npm");
            userDao.createIfNotExists(usr);

            User usr2 = userDao.queryForId("npm");
            Logger.info("usr:"+usr2.getName());
            conn.close();

        } catch (Exception e){
            Logger.error("Exception in init:"+ e +": "+ e.getMessage()+"\n"+Arrays.toString(e.getStackTrace()));
        }
    }
}
