package Utils;

import java.util.Calendar;
import java.util.List;
import java.util.function.Supplier;

//Simple Timer, only have date.Because we can find report by date, and after some day we can earn interest or pay fee.
public class Timer implements Runnable{
    private static long startTime;
    // 10s = 1h
    private static float timeRatio=360;
    private Calendar calendar;

    private List<TimerObserver> observerList;

    private static Timer instance = null;

    public static void init() {
        instance = new Timer();
        // run on a new thread
        try{
            Thread t = new Thread(instance);
            t.start();
            Logger.info("Timer started at time "+System.currentTimeMillis());
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
        this.timeRatio = timeRatio;
        this.startTime = System.currentTimeMillis();
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    public void addTimerObserver(TimerObserver timerObserver){
        observerList.add(timerObserver);
    }
    /*
    public synchronized void addIntervalObserver(Supplier<Integer> callback) {
        observerList.add(callback);
    }

    public static long getStartTime(){
        return startTime;
    }
     */


    public static double getTimeRatio(){
        return timeRatio;
    }

    public int getCurrentDate(){
        return calendar.get(Calendar.DATE);
    }

    @Override
    public void run() {
        while(true){
            //10 s = 1 hr
            try {
                Thread.sleep(240 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance.calendar.add(Calendar.DATE, 1);
            for(TimerObserver timerObserver: observerList){
                timerObserver.timeChange();
            }
        }
    }
}
