package bankBackend.service.impl;

import Utils.Logger;
import bankBackend.Constants;
import bankBackend.entity.DateTime;
import bankBackend.entity.account.LoanAccount;
import bankBackend.entity.account.SavingAccount;
import bankBackend.service.DateTimeService;
import bankBackend.service.SvcMgr;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

class ConsumerInfo {
    BiConsumer<Integer, Integer> consumer;
    int interval;

    public ConsumerInfo(BiConsumer<Integer, Integer> consumer, int interval) {
        this.consumer = consumer;
        this.interval = interval;
    }
}

public class DateTimeCtl implements DateTimeService, Runnable {

    private static DateTimeCtl instance = null;

    private Map<String, ConsumerInfo> observers;

    public static void init() {
        instance = new DateTimeCtl();
        // run on a new thread
        try {
            Thread t = new Thread(instance);
            t.start();
            Logger.info("Timer started at time " + System.currentTimeMillis());
        } catch (Exception e) {
            Logger.fatal(e.getMessage());
        }
        instance.addTimerObserver("generateInterest", SavingAccount::generateInterestCallback, 24);
        instance.addTimerObserver("generateLoanInterest", LoanAccount::generateLoanInterestCallback, 24);
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

    private DateTimeCtl() {
        observers = new HashMap<>();
        DateTime.init();
    }

    public static DateTimeCtl getInstance() {
        if (instance == null) {
            instance = new DateTimeCtl();
        }
        return instance;
    }

    @Override
    public int createNewDate() {
        DateTime dt = new DateTime(System.currentTimeMillis(), Constants.TIMER_RATIO);
        try {
            DateTime.dao.create(dt);
        } catch (Exception e) {
            Logger.fatal(e.getMessage());
        }
        Logger.info("New date created: " + dt.getDate());
        return dt.getDate();
    }


    @Override
    public DateTime getCurrentDate() {
        // query for the last date
        DateTime currentDate = DateTime.getLastDateRecord();
        assert currentDate != null;
        return currentDate;
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
            DateTime currentDate = SvcMgr.getDateTimeService().getCurrentDate();
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
            SvcMgr.getDateTimeService().createNewDate();
        }
    }
}
