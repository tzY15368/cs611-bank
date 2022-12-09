package bankBackend.service.impl;

import Utils.Logger;
import Utils.Result;
import bankBackend.service.SessionService;

public class SessionCtl implements SessionService {
    private static ISession session;

    public static void setSession(ISession session) {
        SessionCtl.session = session;
    }

    public static Result<ISession> getSession() {
        if (session == null) {
            Logger.warn("No session found");
            return new Result<ISession>(false, "No session", null);
        }
        return new Result<ISession>(true, null, session);
    }
}
