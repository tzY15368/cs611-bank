package Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

//Simple Timer, only have date.Because we can find report by date, and after some day we can earn interest or pay fee.
public class Timer implements Runnable {

    class ConsumerInfo {
        BiConsumer<Integer, Integer> consumer;
        int interval;

        public ConsumerInfo(BiConsumer<Integer, Integer> consumer, int interval) {
            this.consumer = consumer;
            this.interval = interval;
        }
    }

    private Map<String, ConsumerInfo> observers;

    private static Timer instance = null;

    public static void init() {
        instance = new Timer();
        // run on a new thread
        try {
            Thread t = new Thread(instance);
            t.start();
            Logger.info("Timer started at time " + System.currentTimeMillis());
        } catch (Exception e) {
            Logger.fatal(e.getMessage());
        }

    }

    public static Timer getInstance() {
        if (instance == null) {
            Logger.warn("Timer not initialized, initializing now");
            init();
        }
        return instance;
    }

    private Timer() {
        observers = new HashMap<>();
        DateCtl.init();
    }

    // interval: virtual hours
    public void addTimerObserver(String name, BiConsumer<Integer, Integer> timerObserver, int scheduleInterval) {
        if (scheduleInterval < 1) {
            Logger.warn("Interval too short, set to 1");
            scheduleInterval = 1;
        }
        observers.put(name, new ConsumerInfo(timerObserver, scheduleInterval));
    }

    public int getCurrentDate() {
        return DateCtl.getCurrentDate().getDate();
    }

    // WARNING: SCHEDULE PERIOD lower bound is 1 hour
    @Override
    public void run() {

        while (true) {
            // time ratio can be changed on a virtual day-to-day basis
            int timerRatio = Constants.TIMER_RATIO;
            // sleep for one hour virtual time
            float virtualHourInRealMs = 3600000 / timerRatio;
            Logger.info("Timer ratio is " + timerRatio +
                    ", virtual hour in real seconds is " + virtualHourInRealMs / 1000);
            // create current date
            int currentDate = DateCtl.createNewDate();
            try {
                float secPerHour = virtualHourInRealMs / 1000;
                for (int i = 0; i < 24; i++) {
                    Thread.sleep((long) virtualHourInRealMs);
                    if (secPerHour <= 1) {
                        Logger.info("Hour = " + i);
                    }
                    // SCHEDULE TIMED JOBS
                    for (String name : observers.keySet()) {
                        Logger.info(String.format("Running task %s", name));
                        ConsumerInfo consumerInfo = observers.get(name);
                        if (i % consumerInfo.interval == 0) {
                            consumerInfo.consumer.accept(currentDate, i);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Logger.warn("Timer thread interrupted" + e);
                System.exit(1);
            }
        }
    }
}
