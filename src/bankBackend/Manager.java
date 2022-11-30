package bankBackend;

import java.util.Date;

public class Manager extends User{
    public Report getReport(){
        // date is generated upon request
        Report report=new Report();
        return report;
    }
}
