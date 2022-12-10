package bankBackend.service;

import bankBackend.entity.DateTime;

import java.util.function.BiConsumer;

public interface DateTimeService {
    int getTimeRatio();

    void setTimeRatio(int ratio);

    DateTime getCurrentDate();

    int getCurrentEpoch();

    boolean addTimerObserver(String name, BiConsumer<Integer, Integer> timerObserver, int scheduleInterval);
}
