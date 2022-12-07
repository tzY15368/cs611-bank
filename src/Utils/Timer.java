package Utils;

import bankBackend.SavingAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

// general task scheduler with dynamic time ratios to real world time
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

    // register observers here
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
        instance.addTimerObserver("generateInterest", SavingAccount::generateInterestCallback, 24);
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
    public boolean addTimerObserver(String name, BiConsumer<Integer, Integer> timerObserver, int scheduleInterval) {
        if (scheduleInterval < 1) {
            Logger.warn("Interval too short, set to 1");
            scheduleInterval = 1;
        } else if (scheduleInterval > 24) {
            Logger.warn("Interval too long, set to 24");
            scheduleInterval = 24;
        }
        observers.put(name, new ConsumerInfo(timerObserver, scheduleInterval));
        Logger.info("Timer observer added: " + name);
        return true;
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
            // get current date
            DateCtl currentDate = DateCtl.getCurrentDate();
            try {
                float secPerHour = virtualHourInRealMs / 1000;
                Logger.info("Timer: starting from hour " + currentDate.getCurrentHour());
                for (int i = currentDate.getCurrentHour() + 1; i < 24; i++) {
                    if (secPerHour <= 1) {
                        Logger.info("Hour = " + i);
                    }
                    // SCHEDULE TIMED JOBS
                    for (String name : observers.keySet()) {
                        ConsumerInfo consumerInfo = observers.get(name);
                        if (i % consumerInfo.interval == 0) {
                            Logger.info(String.format("Running task %s", name));
                            consumerInfo.consumer.accept(currentDate.getDate(), i);
                        }
                    }
                    // update hour
                    currentDate.setCurrentHour(i);
                    Thread.sleep((long) virtualHourInRealMs);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Logger.warn("Timer thread interrupted" + e);
                System.exit(1);
            }
            // the day has passed, start with a new date...
            DateCtl.createNewDate();
        }
    }
}
