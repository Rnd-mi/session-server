package dao;

import dataSets.UsersDataSet;
import org.hibernate.Session;

public class UsersDao {
    private final Session session;

    public UsersDao(Session session) {
        this.session = session;
    }

    public UsersDataSet get(String login) {
        return (UsersDataSet) session.get(UsersDataSet.class, login);
    }

    public void insertUser(String login, String password) {
        session.save(new UsersDataSet(login, password));
    }

    public void deleteUser(UsersDataSet dataSet) {
        session.delete(dataSet);
    }
}
