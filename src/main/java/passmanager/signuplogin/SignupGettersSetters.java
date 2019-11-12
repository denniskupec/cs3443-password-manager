package passmanager.signuplogin;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SignupGettersSetters {

    private String password;

    public SignupGettersSetters() {
        this( null);
    }

    public SignupGettersSetters(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
