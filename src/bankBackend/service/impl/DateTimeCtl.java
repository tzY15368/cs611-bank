package bankBackend.service.impl;

import Utils.Logger;
import bankBackend.Constants;
import bankBackend.entity.DateTime;
import bankBackend.entity.account.LoanAccount;
import bankBackend.entity.account.SavingAccount;
import bankBackend.service.DateTimeService;

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
    private int timeRatio = Constants.DEFAULT_TIMER_RATIO;
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
        instance.addTimerObserver("generateSaveInterest", SavingAccount::generateInterestCallback, 24);
        instance.addTimerObserver("generateLoanInterest", LoanAccount::generateLoanInterestCallback, 24);
    }

    // epochs: task A runs once every [epochs] hours
    public boolean addTimerObserver(String name, BiConsumer<Integer, Integer> timerObserver, int epochs) {
        if (epochs < 1) {
            Logger.warn("Interval too short, set to 1");
            epochs = 1;
        } else if (epochs > 24) {
            Logger.warn("Interval too long, set to 24");
            epochs = 24;
        }
        observers.put(name, new ConsumerInfo(timerObserver, epochs));
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

    private int createNewDate() {
        DateTime dt = new DateTime(System.currentTimeMillis(), Constants.DEFAULT_TIMER_RATIO);
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

    @Override
    public int getCurrentEpoch() {
        DateTime currentDate = this.getCurrentDate();
        int epoch = currentDate.getDate() * Constants.HOUR_PER_DAY + currentDate.getCurrentHour();
        return epoch;
    }

    private int incrEpoch() {
        int currentEpoch = getCurrentEpoch();
        if (currentEpoch == Constants.HOUR_PER_DAY - 1) {
            int dt = createNewDate();
            return Constants.HOUR_PER_DAY * dt;
        } else {
            DateTime d = getCurrentDate();
            d.setCurrentHour(d.getCurrentHour() + 1);
            return d.getCurrentHour();
        }
    }

    @Override
    public int getTimeRatio() {
        return timeRatio;
    }

    @Override
    public void setTimeRatio(int ratio) {
        if (ratio > 86400 || ratio < 360) {
            Logger.warn("invalid op: 360 < ratio < 86400");
        } else {
            this.timeRatio = ratio;
        }
    }

    // WARNING: SCHEDULE PERIOD lower bound is 1 hour
    @Override
    public void run() {
        // 做成WAL，首先更新小时，再做相应操作
        // 假设查到的date=3, hour=2, epoch=74
        // 则：
        // 1. 首先回滚所有epoch==74的事务
        // 2. 从epoch=74开始重新进入事件循环
        int bootEpoch = getCurrentEpoch();
        long sleepMsPerHour = (long) ((60 * 60 * 1000) / (float) getTimeRatio());
        Logger.info("DateTimeService: booting from epoch=" + bootEpoch + " , defaultSleepS=" + sleepMsPerHour / 1000);
        try {
            while (true) {
                // 如果bootepoch == currentepoch,则不用更新小时
                int currentEpoch = getCurrentEpoch();
                if (bootEpoch != currentEpoch) {
                    currentEpoch = incrEpoch();
                } else {
                    // TODO: rollback everything at and after currentEpoch
                }

                // calculate virtualhourinrealms
                if (currentEpoch % Constants.HOUR_PER_DAY == 0) {
                    int currentTimeRatio = getTimeRatio();
                    float virtualHourInRealMs = (60 * 60 * 1000) / (float) currentTimeRatio;
                    sleepMsPerHour = (long) virtualHourInRealMs;
                    Logger.info(String.format("Ticker: epoch=%d, sleepS=%d", currentEpoch, sleepMsPerHour / 1000));
                }

                // run scheduled jobs
                for (String name : observers.keySet()) {
                    ConsumerInfo ci = observers.get(name);
                    if (currentEpoch % ci.interval == 0) {
                        Logger.info(String.format("Running task %s", name));
                        int date = currentEpoch % Constants.HOUR_PER_DAY;
                        int hour = currentEpoch - (Constants.HOUR_PER_DAY * date);
                        ci.consumer.accept(date, hour);
                    }
                }
                Thread.sleep(sleepMsPerHour);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.warn("Timer thread interrupted" + e);
            System.exit(1);
        }
    }
}
