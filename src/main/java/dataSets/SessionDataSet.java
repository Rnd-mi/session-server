package dataSets;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "sessions")
public class SessionDataSet implements Serializable {
    private final static long serialVersionUID = 8471941411L;

    @Id
    @Column(name = "session")
    private String session;

    @Column(name = "login", unique = true)
    private String login;

    @SuppressWarnings("UnusedDeclaration")
    public SessionDataSet() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public SessionDataSet(String session, String login) {
        this.session = session;
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
