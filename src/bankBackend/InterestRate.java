package bankBackend;

import Utils.DBManager;
import Utils.Result;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

enum RateType {
    Save,
    Loan,
}

class InterestRateManager {
    private InterestRateManager instance;

    private InterestRateManager() {

    }

    public InterestRateManager getInstance() {
        if (instance == null) {
            instance = new InterestRateManager();
        }
        return instance;
    }

    public Result<Void> setInterestRate(InterestRate rate) {
        try {
            InterestRate.dao.createOrUpdate(rate);
            return new Result<Void>();
        } catch (Exception e) {
            return new Result<Void>(false, e.getMessage(), null);
        }
    }

    public Result<Integer> getInterestRate(int accountId, RateType type) {
        try {
            InterestRate rate = InterestRate.dao.queryBuilder().where().eq("accountId", accountId)
                    .and().eq("type", type).queryForFirst();
            if (rate == null) {
                return new Result<Integer>(false, "Interest rate not found", null);
            }
            return new Result<Integer>(true, "", rate.getRate());
        } catch (Exception e) {
            return new Result<Integer>(false, e.getMessage(), null);
        }
    }
}

@DatabaseTable(tableName = "SaveInterestRate")
public class InterestRate {
    static Dao<InterestRate, Integer> dao = DBManager.getDao(InterestRate.class);

    @DatabaseField(unique = true)
    private int accountId;

    // percentage in value
    @DatabaseField
    private int rate;

    @DatabaseField
    private RateType type;

    public InterestRate() {
        // ORMLite needs a no-arg constructor
    }

    public InterestRate(int accountId, int rate, RateType type) {
        this.accountId = accountId;
        this.rate = rate;
        this.type = type;
    }

    public int getRate() {
        return rate;
    }
}

