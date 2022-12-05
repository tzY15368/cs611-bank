package Utils;

import bankBackend.User;

public interface ISession {

    public User getUser();

    public void set(String key, Object value);

    public Result<Object> get(String key);

    public void remove(String key);

    public void clear();
}