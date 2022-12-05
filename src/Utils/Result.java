package Utils;

public class Result<T> {
    public boolean success;
    public String msg;
    public T data;

    public Result() {
        // equivalent to returns void
        this.success = true;
        this.msg = "";
        this.data = null;
    }

    public Result(T data) {
        // equivalent to return T data
        this.success = true;
        this.msg = null;
        this.data = data;
    }

    public Result(boolean success, String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    // data MUST NOT be null
    // USE WITH CAUTION, UNWRAP MAY FAIL CATASTROPHICALLY AND CALL SYSTEM.EXIT(-1)
    // SEE IMPL OF LOGGER.FATAL
    public T unwrap() {
        if (this.data == null) {
            Logger.fatal("Result unwrap failed, data is null");
        }
        return data;
    }

    @Override
    public String toString() {
        return (this.success ? "ok" : "fail") + ": " + this.msg;
    }
}
