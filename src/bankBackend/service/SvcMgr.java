package bankBackend.service;

import bankBackend.dao.DaoManager;
import bankBackend.service.impl.*;

public class SvcMgr {

    public static void init() {

        // init dao first
        DaoManager.init();

        // note that this would start a new thread.
        DateTimeCtl.init();

        // init services
        StockCtl.init();
        UserCtl.init();
    }

    public static SQLService getSqlService() {
        return new SqlCtl();
    }

    public static DateTimeService getDateTimeService() {
        return DateTimeCtl.getInstance();
    }

    public static SessionService getSessionService() {
        return SessionCtl.getInstance();
    }

    public static UserService getUserService() {
        return UserCtl.getInstance();
    }

    public static StockService getStockService() {
        return StockCtl.getInstance();
    }

    public static InterestRateService getInterestRateService() {
        return InterestRateCtl.getInstance();
    }

    public static AccountService getAccountService() {
        return AccountCtl.getInstance();
    }
}
