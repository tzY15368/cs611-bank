package bankBackend.service;

import bankBackend.entity.DateTime;

import java.util.function.BiConsumer;

public interface DateTimeService {
    int createNewDate();

    DateTime getCurrentDate();

    boolean addTimerObserver(String name, BiConsumer<Integer, Integer> timerObserver, int scheduleInterval);
}