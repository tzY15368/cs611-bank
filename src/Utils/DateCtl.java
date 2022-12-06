package Utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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

    public DateCtl() {
        // ORMLite needs a no-arg constructor
    }

    public int getDate() {
        return date;
    }

    // init MUST run before everything else
    public static void init() {
        try {
            List<DateCtl> dateCtlList = dao.queryForAll();
            if (dateCtlList.size() == 0) {
                DateCtl dateCtl = new DateCtl();
                dateCtl.date = 0;
                dateCtl.startTimestamp = System.currentTimeMillis();
                dateCtl.timeRatio = Constants.TIMER_RATIO;
                dao.create(dateCtl);
            }
        } catch (Exception e) {
            Logger.fatal(e.getMessage());
        }
    }

    public static DateCtl getCurrentDate() {
        // query for the last date
        try {

            List<DateCtl> dates = dao.queryForAll();
            assert dates.size() != 0;
            return dates.get(dates.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.fatal("date error:" + e.toString());
        }
        return null;
    }

    public static int getHourOfDay() {
        return (int) ((System.currentTimeMillis() - getCurrentDate().startTimestamp) / (1000 * Constants.TIMER_RATIO));
    }
}
