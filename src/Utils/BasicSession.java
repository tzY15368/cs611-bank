package Utils;

import bankBackend.User;

import java.util.Map;

public class BasicSession implements ISession {
    private User user;
    private Map<String, Object> data;

    public BasicSession(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public void set(String key, Object value) {
        data.put(key, value);
    }

    @Override
    public Result<Object> get(String key) {
        if (data.containsKey(key)) {
            return new Result<Object>(true, null, data.get(key));
        }
        return new Result<Object>(false, "No such key", null);
    }

    @Override
    public void remove(String key) {
        data.remove(key);
    }

    @Override
    public void clear() {
        data.clear();
    }
}
