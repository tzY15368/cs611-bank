package bankBackend.entity;

import bankBackend.dao.DaoManager;
import bankBackend.entity.enums.RateType;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "SaveInterestRate")
public class InterestRate {
    public static Dao<InterestRate, Integer> dao = DaoManager.getDao(InterestRate.class);

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

    public InterestRate(int accountId, RateType type) {
        this.accountId = accountId;
        this.rate = 0;
        this.type = type;
    }

    public int getRate() {
        return rate;
    }
}

