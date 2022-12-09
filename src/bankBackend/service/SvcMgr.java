package bankBackend.service;

import bankBackend.service.impl.*;

public class SvcMgr {
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
