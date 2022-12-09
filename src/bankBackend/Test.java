package bankBackend;

import bankBackend.entity.User;
import bankBackend.service.SvcMgr;

public class Test {
    public static void main(String[] args) {
        BankBackend bnk = new BankBackend();

        // session should be up
        User u = SvcMgr.getSessionService().getSession().data.getUser();
        System.out.println("usr-got:" + u.getName());
    }
}
