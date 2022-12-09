package bankBackend.factory;

import bankBackend.entity.User;
import bankBackend.service.SessionService;

import java.util.Map;

public class MemorySession implements SessionService {
    private User user;

    public MemorySession(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
