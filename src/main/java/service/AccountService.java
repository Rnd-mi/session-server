package service;

import dao.SessionDAO;
import dao.UsersDao;
import dataSets.SessionDataSet;
import dataSets.UsersDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class AccountService {
    private final static String hibernate_show_sql = "true";
    private final static String hibernate_hbm2ddl_auto = "validate";

    private final SessionFactory factory;

    public AccountService() {
        Configuration configuration = getMyPostgresCfg();
        this.factory = createSessionFactory(configuration);
    }

    private Configuration getMySqlCfg() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);
        configuration.addAnnotatedClass(SessionDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example");
        configuration.setProperty("hibernate.connection.username", "admin");
        configuration.setProperty("hibernate.connection.password", "admin");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);

        return configuration;
    }

    private Configuration getMyH2Cfg() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "admin");
        configuration.setProperty("hibernate.connection.password", "admin");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);

        return configuration;
    }

    private Configuration getMyPostgresCfg() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);
        configuration.addAnnotatedClass(SessionDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/db_servlet_app");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "Evolution2001");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);

        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public UsersDataSet getUser(String login) {
        Session session = factory.openSession();
        UsersDao dao = new UsersDao(session);
        UsersDataSet dataSet = dao.get(login);
        session.close();
        return dataSet;
    }

    public void saveToDB(String login, String password) {
        Session session = factory.openSession();
        Transaction trs = session.beginTransaction();
        UsersDao dao = new UsersDao(session);
        dao.insertUser(login, password);
        trs.commit();
        session.close();
    }

    public void addSession(String sessionID, UsersDataSet dataSet) {
        Session session = factory.openSession();
        Transaction trs = session.beginTransaction();
        SessionDAO dao = new SessionDAO(session);
        dao.saveSessionID(sessionID, dataSet.getLogin());
        trs.commit();
        session.close();
    }

    public void deleteSession(String sessionID) {
        Session session = factory.openSession();
        Transaction trs = session.beginTransaction();
        SessionDAO dao = new SessionDAO(session);
        SessionDataSet dataSet = dao.getUserBySession(sessionID);
        dao.deleteSession(dataSet);
        trs.commit();
        session.close();
    }

    public SessionDataSet getUserBySessionID(String sessionID) {
        Session session = factory.openSession();
        SessionDAO dao = new SessionDAO(session);
        SessionDataSet dataSet = dao.getUserBySession(sessionID);
        session.close();
        return dataSet;
    }

    public void deleteProfile(UsersDataSet dataSet) {
        Session session = factory.openSession();
        Transaction trs = session.beginTransaction();
        UsersDao dao = new UsersDao(session);
        dao.deleteUser(dataSet);
        trs.commit();
        session.close();
    }
}
