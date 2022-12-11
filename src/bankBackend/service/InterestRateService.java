package bankBackend.service;

import Utils.Result;
import bankBackend.entity.InterestRate;
import bankBackend.entity.enums.RateType;

public interface InterestRateService {
    Result<Void> setInterestRate(InterestRate rate);

    Result<InterestRate> getInterestRate(int accountId, RateType type);

    // manager-only func
    int getGlobalInterestRate();

    // manager-only func
    void setGlobalInterestRate(int rate);
}
