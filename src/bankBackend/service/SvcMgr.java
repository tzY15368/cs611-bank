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
}
