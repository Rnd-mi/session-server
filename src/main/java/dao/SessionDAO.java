package dao;

import dataSets.SessionDataSet;
import org.hibernate.Session;

public class SessionDAO {

    private final Session session;

    public SessionDAO(Session session) {
        this.session = session;
    }

    public void saveSessionID(String sessionID, String login) {
        session.save(new SessionDataSet(sessionID, login));
    }

    public SessionDataSet getUserBySession(String sessionID) {
        return (SessionDataSet) session.get(SessionDataSet.class, sessionID);
    }

    public void deleteSession(SessionDataSet dataSet) {
        session.delete(dataSet);
    }
}
