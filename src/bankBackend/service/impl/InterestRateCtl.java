package bankBackend.service.impl;

import Utils.Result;
import bankBackend.entity.InterestRate;
import bankBackend.entity.enums.RateType;
import bankBackend.service.InterestRateService;

public class InterestRateCtl implements InterestRateService {
    private InterestRateCtl instance;

    private InterestRateCtl() {

    }

    public InterestRateCtl getInstance() {
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
