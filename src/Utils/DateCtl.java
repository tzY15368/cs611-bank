package Utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.List;

@DatabaseTable(tableName = "DateUtil")
public class DateCtl {
    static Dao<DateCtl, Integer> dao = DBManager.getDao(DateCtl.class);

    @DatabaseField(generatedId = true)
    private int date;

    @DatabaseField
    private long startTimestamp;

    @DatabaseField
    private float timeRatio;

    @DatabaseField
    private int elapsedHours;

    public DateCtl() {
        // ORMLite needs a no-arg constructor
    }

    public int getDate() {
        return date;
    }

    // init MUST run before everything else
    public static void init() {
        try {
            DateCtl lastRecordedDate = getLastDateRecord();
            if(lastRecordedDate==null){
                DateCtl dateCtl = new DateCtl();
                dateCtl.date = 0;
                dateCtl.startTimestamp = System.currentTimeMillis();
                dateCtl.timeRatio = Constants.TIMER_RATIO;
                dao.create(dateCtl);
                Logger.info("DateCtl first-time initialized, latest date is " + dateCtl.date);
            } else {
                // TODO: ROLLBACK ALL TRANSACTIONS THAT HAPPEND TILL THAT HOUR
                Logger.info(String.format("DateCtl re-initialized with date %d hour %d" , lastRecordedDate.date, lastRecordedDate.elapsedHours));
            }
        } catch (Exception e) {
            Logger.fatal(e.getMessage());
        }
    }

    // WARNING: NULLABLE
    private static DateCtl getLastDateRecord(){
        try{
            return dao.queryBuilder().orderBy("date", false).limit(1L).query().get(0);
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return null;
        }
    }

    public static int createNewDate() {
        DateCtl dateCtl = new DateCtl();
        dateCtl.startTimestamp = System.currentTimeMillis();
        dateCtl.timeRatio = Constants.TIMER_RATIO;
        try {
            dao.create(dateCtl);
        } catch (Exception e) {
            Logger.fatal(e.getMessage());
        }
        Logger.info("New date created: " + dateCtl.date);
        return dateCtl.date;
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

    public static DateCtl getCurrentDate() {
        // query for the last date
        DateCtl currentDate = DateCtl.getLastDateRecord();
        assert currentDate!=null;
        return currentDate;
    }

    public static int getHourOfDay() {
        return (int) ((System.currentTimeMillis() - getCurrentDate().startTimestamp) / (1000 * Constants.TIMER_RATIO));
    }
}
