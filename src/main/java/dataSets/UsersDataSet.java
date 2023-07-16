package dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class UsersDataSet implements Serializable {
    private static final long serialVersionUID = 1419471908471L;

    @Id
    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @SuppressWarnings("UnusedDeclaration")
    public UsersDataSet() {

    }

    @SuppressWarnings("UnusedDeclaration")
    public UsersDataSet(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UsersDataSet{" +
                " login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}
