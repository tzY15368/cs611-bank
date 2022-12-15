package bankBackend.dao;

import bankBackend.Config;
import bankBackend.entity.DateTime;
import Utils.Logger;
import bankBackend.entity.*;
import bankBackend.entity.account.Account;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DaoManager {
    private static ConnectionSource conn;
    private static boolean didInit = false;
    private static Map<Class, Dao> daoMap;

    public static void init() {
        try {

            conn = new JdbcConnectionSource(Config.DB_URL);
            didInit = true;
            daoMap = new HashMap<>();

            // The type params are not actually required,
            // as long as the caller of getDao knows the type of the second argument,
            // see User.java
            Class[] classes = {
                    User.class,
                    Account.class,
                    Balance.class,
                    Stock.class,
                    Transaction.class,
                    InterestRate.class,
                    DateTime.class,
            };
            for (Class c : classes) {
                TableUtils.createTableIfNotExists(conn, c);
                daoMap.put(c, com.j256.ormlite.dao.DaoManager.createDao(conn, c));
            }
        } catch (SQLException e) {
            Logger.fatal("Daomanager::init:" + e.getMessage());
        }
    }

    public static ConnectionSource getConn() {
        if (!didInit) {
            Logger.error("DBManager not initialized!, call init first");
            System.exit(-1);
        }
        return conn;
    }

    public static Dao getDao(Class clazz) {
        Dao d = daoMap.get(clazz);
        if (d == null) {
            Logger.error("Dao not found for class:" + clazz);
            System.exit(-1);
        }
        return d;
    }

    public static void close() throws Exception {
        try {
            conn.close();
        } catch (SQLException e) {
            Logger.error("Exception in close:" + e);
        }
    }
}
