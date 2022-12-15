package bankBackend.service.impl;

import bankBackend.entity.User;
import bankBackend.service.SQLService;
import com.j256.ormlite.dao.GenericRawResults;

public class SqlCtl implements SQLService {
    @Override
    public String runSql(String sql) {
        try {

            GenericRawResults<String[]> r = User.dao.queryRaw(sql);
            StringBuilder sb = new StringBuilder();
            for (String[] s : r) {
                for (String s1 : s) {
                    sb.append(s1).append(" ");
                }
                sb.append("\n\r");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }
}

