package passmanager;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SignupGettersSetters {

    private String user;
    private String password;

    public SignupGettersSetters() {
        this(null, null);
    }

    public SignupGettersSetters(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String login) {
        this.user = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
