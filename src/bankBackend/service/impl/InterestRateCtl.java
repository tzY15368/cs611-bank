package bankBackend.service.impl;

import Utils.Result;
import bankBackend.Constants;
import bankBackend.entity.InterestRate;
import bankBackend.entity.enums.RateType;
import bankBackend.service.InterestRateService;

public class InterestRateCtl implements InterestRateService {
    private static InterestRateCtl instance;
    private int globalInterestRate;

    private InterestRateCtl() {
        this.globalInterestRate = Constants.DEFAULT_SAVE_INTEREST_RATE;
    }

    public int getGlobalInterestRate() {
        return this.globalInterestRate;
    }

    public void setGlobalInterestRate(int rate) {
        this.globalInterestRate = rate;
    }

    public static InterestRateCtl getInstance() {
        if (instance == null) {
            instance = new InterestRateCtl();
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

    public Result<InterestRate> getInterestRate(int accountId, RateType type) {
        try {
            InterestRate rate = InterestRate.dao.queryBuilder().where().eq("accountId", accountId)
                    .and().eq("type", type).queryForFirst();
            if (rate == null) {
                return new Result<>(false, "Interest rate not found", null);
            }
            return new Result<>(true, "", rate);
        } catch (Exception e) {
            return new Result<>(false, e.getMessage(), null);
        }
    }
}
