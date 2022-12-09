package bankBackend.service.impl;

import Utils.Logger;
import Utils.Result;
import bankBackend.service.ISession;
import bankBackend.service.SessionService;

public class SessionCtl implements SessionService {
    private static SessionCtl instance = null;
    private ISession session;

    private SessionCtl() {
    }

    public static SessionCtl getInstance() {
        if (instance == null) {
            instance = new SessionCtl();
        }
        return instance;
    }

    @Override
    public void setSession(ISession session) {
        this.session = session;
    }

    @Override
    public Result<ISession> getSession() {
        if (session == null) {
            Logger.warn("No session found");
            return new Result<ISession>(false, "No session", null);
        }
        return new Result<ISession>(true, null, session);
    }
}
