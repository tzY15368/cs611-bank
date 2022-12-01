package Utils;

public class SessionMgr {
    private static ISession session;

    public static void setSession(ISession session) {
        SessionMgr.session = session;
    }

    public static Result<ISession> getSession() {
        if (session == null) {
            return new Result<ISession>(false, "No session", null);
        }
        return new Result<ISession>(true, null, session);
    }
}
