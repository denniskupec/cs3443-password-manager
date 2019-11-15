package passmanager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class accountGettersSetters {
    private final StringProperty title;
    private final StringProperty url;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty note;

    public accountGettersSetters(String title, String url, String username, String password, String note) {
        this.title = new SimpleStringProperty(title);
        this.url = new SimpleStringProperty(url);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.note = new SimpleStringProperty(note);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getUrl() {
        return url.get();
    }

    public StringProperty urlProperty() {
        return url;
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }
}
