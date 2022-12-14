package bankBackend.service.impl;

import Utils.Logger;
import Utils.Result;
import bankBackend.Constants;
import bankBackend.entity.InterestRate;
import bankBackend.entity.enums.CurrencyType;
import bankBackend.entity.enums.IRCalcMethod;
import bankBackend.entity.enums.RateType;
import bankBackend.service.InterestRateService;
import bankBackend.service.SvcMgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InterestRateCtl implements InterestRateService {
    private static InterestRateCtl instance;
    private Map<RateType, Integer> globalInterestRate;

    private InterestRateCtl() {
        this.globalInterestRate = Constants.DEFAULT_INTEREST_RATE;
        Logger.info("InterestRateCtl initialized with default interest rate:" + globalInterestRate);
    }

    public static InterestRateCtl getInstance() {
        if (instance == null) {
            instance = new InterestRateCtl();
        }
        return instance;
    }

    @Override
    public Result<Void> createInterestRate(int accountId, int rate, RateType type, int startEpoch, int endEpoch,
                                           String description, int collat_user_id, int initValue,
                                           IRCalcMethod method, CurrencyType currencyType) {
        InterestRate ir = new InterestRate(accountId, rate, type, startEpoch,
                endEpoch, description, collat_user_id, initValue, method, currencyType);
        try {
            InterestRate.dao.create(ir);
            return new Result<>();
        } catch (Exception e) {
            return new Result<>(false, e.getMessage(), null);
        }
    }

    @Override
    public List<InterestRate> getInterestRate(int accountId, RateType type) {
        try {
            return InterestRate.dao.queryBuilder()
                    .where().eq("account_id", accountId)
                    .and().eq("type", type)
                    .and().le("endEpoch", SvcMgr.getDateTimeService().getCurrentEpoch())
                    .query();
        } catch (Exception e) {
            Logger.error("getInterestRate:" + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public int getGlobalInterestRate(RateType type) {
        return globalInterestRate.get(type);
    }

    @Override
    public void setGlobalInterestRate(RateType type, int rate) {
        globalInterestRate.put(type, rate);
    }
}
