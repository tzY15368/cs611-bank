package bankBackend.entity;

import Utils.Logger;
import Utils.Result;
import bankBackend.Config;
import bankBackend.dao.DaoManager;
import bankBackend.entity.enums.CurrencyType;
import bankBackend.entity.enums.IRCalcMethod;
import bankBackend.entity.enums.RateType;
import bankBackend.service.SvcMgr;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "SaveInterestRate")
public class InterestRate {
    public static Dao<InterestRate, Integer> dao = DaoManager.getDao(InterestRate.class);

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(unique = true)
    private int accountId;

    // percentage in value
    @DatabaseField
    private int rate;

    @DatabaseField
    private RateType type;

    @DatabaseField
    private int startEpoch;

    @DatabaseField
    private int endEpoch;

    @DatabaseField
    private String description;

    @DatabaseField
    private int collat_user_id;

    @DatabaseField
    private int initValue;

    @DatabaseField
    private IRCalcMethod method;

    @DatabaseField
    private CurrencyType currencyType;

    @DatabaseField
    private int coveredAmount;

    public InterestRate() {
        // ORMLite needs a no-arg constructor
    }

    public InterestRate(int accountId, int rate, RateType type, int startEpoch, int endEpoch, String description,
                        int collat_user_id, int initValue, IRCalcMethod method, CurrencyType currencyType) {
        this.accountId = accountId;
        this.rate = rate;
        this.type = type;
        this.startEpoch = startEpoch;
        this.endEpoch = endEpoch;
        this.description = description;
        this.collat_user_id = collat_user_id;
        this.initValue = initValue;
        this.method = method;
        this.currencyType = currencyType;
    }

    public String toString() {
        return "InterestRate{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", rate=" + rate +
                ", type=" + type +
                ", startEpoch=" + startEpoch +
                ", endEpoch=" + endEpoch +
                ", description='" + description + '\'' +
                ", collat_user_id=" + collat_user_id +
                ", initValue=" + initValue +
                ", method=" + method +
                ", currencyType=" + currencyType +
                ", coveredAmount=" + coveredAmount +
                '}';
    }

    public int getRate() {
        return rate;
    }

    public int getEndEpoch() {
        return endEpoch;
    }

    public int getStartEpoch() {
        return startEpoch;
    }

    public int getDeltaForEpoch(int epoch) {
        int result = 0;
        switch (method) {
            case Simple:
                result = ((initValue - coveredAmount) * rate / 100);
                break;
            case Compound:
                result = (int) ((initValue - coveredAmount) * Math.pow(
                        (double) (100 + rate) / 100,
                        (epoch - startEpoch) / (Config.DEFAULT_INTEREST_CYCLE_LENGTH.get(this.type) * Config.HOUR_PER_DAY))
                );
        }
        Logger.info(String.format("InterestRate: getDeltaForEpoch%d: %d", epoch, result));
        return result;
    }

    public int getLoanPay() {
        int currentEpoch = SvcMgr.getDateTimeService().getCurrentEpoch();
        int value = initValue * (Config.LOAN_INTEREST_CYCLE_LENGTH / (endEpoch - startEpoch));
        return value;
    }

    public int getCollat_user_id() {
        return collat_user_id;
    }

    public int getPaybackValue() {
        return this.coveredAmount;
    }

    public int getInitValue() {
        return initValue;
    }

    public IRCalcMethod getMethod() {
        return method;
    }

    public String getDescription() {
        return description;
    }

    public CurrencyType getCurrency() {
        return currencyType;
    }

    public int getId() {
        return accountId;
    }

    public int getCoveredAmount() {
        return coveredAmount;
    }

    public Result<Void> setCoveredAmount(int coveredAmount) {
        this.coveredAmount = coveredAmount;
        try {
            dao.update(this);
            return new Result<>();
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return new Result<>(false, e.getMessage(), null);
        }
    }
}

