package bankBackend.service;

import Utils.Result;
import bankBackend.entity.InterestRate;
import bankBackend.entity.enums.CurrencyType;
import bankBackend.entity.enums.IRCalcMethod;
import bankBackend.entity.enums.RateType;

import java.util.List;

public interface InterestRateService {
    Result<Void> createInterestRate(int accountId, RateType type,
                                    int startEpoch, int endEpoch, String description,
                                    int collat_user_id, int initValue, IRCalcMethod method, CurrencyType currencyType);

    List<InterestRate> getInterestRate(int accountId, RateType type);

    // manager-only func
    int getGlobalInterestRate(RateType type);

    // manager-only func
    void setGlobalInterestRate(RateType type, int rate);
}
