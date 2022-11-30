package bankBackend;


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
            Dao<Account, String> accountDao = DaoManager.createDao(conn, Account.class);

            TableUtils.createTable(conn, Account.class);
            Account acc = new Account();
            acc.setName("npm");
            accountDao.create(acc);

            Account acc2 = accountDao.queryForId("npm");
            System.out.println("ACC:"+acc2.getName());
            conn.close();

        } catch (Exception e){
            System.out.println("Exception in init:"+ Arrays.toString(e.getStackTrace()));
        }
    }
}
