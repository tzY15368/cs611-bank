package bankBackend.service;

import bankBackend.entity.DateTime;

public interface DateTimeService {
    int createNewDate();

    int getCurrentHour();

    DateTime getCurrentDate();
}
