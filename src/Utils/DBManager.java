package Utils;

import bankBackend.Account;
import bankBackend.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBManager {
    private static ConnectionSource conn;
    private static boolean didInit = false;
    private static Map<Class, Dao> daoMap;

    public static Result<Void> init() {
        try {

            conn = new JdbcConnectionSource("jdbc:sqlite:bank.db");
            didInit = true;
            daoMap = new HashMap<>();

            // The type params are not actually required,
            // as long as the caller of getDao knows the type of the second argument,
            // see User.java
            Class[] classes = {User.class, Account.class};
            for (Class c : classes) {
                daoMap.put(c, DaoManager.createDao(conn, c));
            }
        } catch (SQLException e) {
            return new Result<Void>(false, "SQL Exception in init:" + e + ": " + e.getMessage(), null);
        }
        return new Result<Void>(true, null, null);
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
