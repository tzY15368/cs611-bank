package bankBackend.entity;

import Utils.Logger;
import bankBackend.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DateTime")
public class DateTime {
    public static Dao<DateTime, Integer> dao = DaoManager.getDao(DateTime.class);

    @DatabaseField(generatedId = true)
    private int date;

    @DatabaseField
    private long startTimestamp;

    @DatabaseField
    private float timeRatio;

    @DatabaseField
    private int elapsedHours;

    public DateTime() {
        // ORMLite needs a no-arg constructor
    }

    public DateTime(long startTimestamp, float timeRatio) {
        this();
        this.startTimestamp = startTimestamp;
        this.timeRatio = timeRatio;
    }

    public int getTimeRatio() {
        return (int) timeRatio;
    }

    public void setHour(int hour) {
        this.elapsedHours = hour;
    }

    public int getDate() {
        return date;
    }

    // WARNING: NULLABLE
    public static DateTime getLastDateRecord() {
        try {
            return dao.queryBuilder().orderBy("date", false).limit(1L).query().get(0);
        } catch (Exception e) {
            Logger.warn("DateTime: getLastRecord: " + e.getMessage());
            return null;
        }
    }

    public int getCurrentHour() {
        return this.elapsedHours;
    }

    public void setCurrentHour(int hour) {
        this.elapsedHours = hour;
        try {
            dao.update(this);
        } catch (Exception e) {
            Logger.fatal(e.getMessage());
        }
    }
}
