package Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

enum LogLevel {
    DEBUG,
    INFO,
    WARN,
    ERROR,
    FATAL
}

// singleton
public class Logger {
    private static Logger instance;
    private List<Consumer<String>> handlers;

    private Logger() {
        this.handlers = new ArrayList<>();
        handlers.add(System.out::println);
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    private void log(LogLevel lvl, String msg) {
        for (Consumer<String> handler : handlers) {
            handler.accept(lvl.name() + ": " + msg);
        }
    }

    public static void fatal(String msg) {
        getInstance().log(LogLevel.FATAL, msg);
        System.exit(-1);
    }

    public static void error(String args) {
        getInstance().log(LogLevel.ERROR, args);
    }

    public static void warn(String args) {
        getInstance().log(LogLevel.WARN, args);
    }

    public static void info(String args) {
        getInstance().log(LogLevel.INFO, args);
    }

    public static void debug(String args) {
        getInstance().log(LogLevel.DEBUG, args);
    }
}
