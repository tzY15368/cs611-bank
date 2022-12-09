package bankBackend.service;

import Utils.Result;

public interface SessionService {
    Result<ISession> getSession();

    void setSession(ISession i);
}
