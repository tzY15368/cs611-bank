package bankBackend;

import java.util.HashMap;

public class Constants {

    public static final String DB_URL = "jdbc:sqlite:bank.db";
    public static int SECURITY_ACC_OPEN_THRESHOLD = 1000;
    public static int SAVING_ACC_INTEREST_THRESHOLD = 1000;
    // WARNING: UPPER BOUND IS 86400, meaning 1 second in real life is 24 hours in simulation, PERFORMANCE MAY SUFFER
    public static int TIMER_RATIO = 3600;
    public static int STOCK_MANAGER_USER_ID = 1;
    public static int BANK_MANAGER_USER_ID = -1;
    public static String BANK_MANAGER_USERNAME = "bankManager";
    public static String STOCK_MARKET_NAME = "StockMarket";

    public static int TX_CHARGE_FEE_VALUE = 50;

    public static int HOUR_PER_DAY = 24;
}
