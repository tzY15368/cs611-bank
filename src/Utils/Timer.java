package Utils;

import java.util.List;
import java.util.function.Supplier;

public class Timer {
    private long startTime;
    private float timeRatio;

    private List<Supplier<Integer>> observers;

    private static Timer instance = null;

    public static void init() {
        instance = new Timer();
    }

    public static Timer getInstance() {
        if (instance == null) {
            Logger.warn("Timer not initialized, initializing now");
            init();
        }
        return instance;
    }

    private Timer() {
        this.timeRatio = timeRatio;
        this.startTime = System.currentTimeMillis();
    }

    public synchronized void addIntervalObserver(Supplier<Integer> callback) {
        observers.add(callback);
    }

}
