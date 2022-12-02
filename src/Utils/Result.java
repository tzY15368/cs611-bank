package Utils;

public class Result<T> {
    private boolean success;
    private String msg;
    private T data;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return (this.success ? "ok" : "fail") + ": " + this.msg;
    }
}
