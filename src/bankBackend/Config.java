package bankBackend;

import bankBackend.entity.enums.RateType;
import bankBackend.entity.enums.TransactionType;

import java.util.HashMap;
import java.util.Map;

public class Config {

    public static final String DB_URL = "jdbc:sqlite:bank.db";
    public static int SECURITY_ACC_OPEN_THRESHOLD = 1000;
    public static int SAVING_ACC_INTEREST_THRESHOLD = 1000;
    // WARNING: UPPER BOUND IS 86400, meaning 1 second in real life is 24 hours in simulation, PERFORMANCE MAY SUFFER
    public static int DEFAULT_TIMER_RATIO = 3600;
    public static int STOCK_MANAGER_USER_ID = 1;
    public static int BANK_MANAGER_USER_ID = -1;
    public static String BANK_MANAGER_USERNAME = "bankManager";
    public static String STOCK_MARKET_NAME = "StockMarket";

    public static int TX_CHARGE_FEE_VALUE = 50;

    public static int HOUR_PER_DAY = 24;

    public static int DEFAULT_SAVE_INTEREST_RATE = 5;
    public static int DEFAULT_LOAN_INTEREST_RATE = 10;

    public static int TXN_MONEY_IO_NULL = -555;

    public static int TXN_NULL_SENDER = -666;
    public static int TXN_NULL_RECEIVER = -777;

    public static int TX_TRANSFER_FEE_VALUE = 5;
    public static int DEPOSIT_FEE_VALUE = 5;
    public static int WITHDRAW_FEE_VALUE = 5;

    public static int SAVE_INTEREST_CYCLE_LENGTH = 48;
    public static int LOAN_INTEREST_CYCLE_LENGTH = 48;

    public static Map<TransactionType, Integer> ALL_CHARGE_FEE_VALUES = new HashMap<>() {
        {
            put(TransactionType.TRANSFER, TX_TRANSFER_FEE_VALUE);
            put(TransactionType.DEPOSIT, DEPOSIT_FEE_VALUE);
            put(TransactionType.WITHDRAW, WITHDRAW_FEE_VALUE);
        }
    };

    public static Map<RateType, Integer> DEFAULT_INTEREST_RATE = new HashMap<>() {
        {
            put(RateType.Save, DEFAULT_SAVE_INTEREST_RATE);
            put(RateType.Loan, DEFAULT_LOAN_INTEREST_RATE);
        }
    };

    public static Map<RateType, Integer> DEFAULT_INTEREST_CYCLE_LENGTH = new HashMap<>() {
        {
            put(RateType.Save, SAVE_INTEREST_CYCLE_LENGTH);
            put(RateType.Loan, LOAN_INTEREST_CYCLE_LENGTH);
        }
    };
}
