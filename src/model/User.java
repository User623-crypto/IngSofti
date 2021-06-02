package model;

public class User {
    private int id;
    private String name;
    private String password;
    private boolean notification_on = true;


    public User(String name, String password) throws IllegalArgumentException{
        setName(name);
        setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length()>45)
            throw new IllegalArgumentException("Emri Nuk mund te jete kaq i gjate");
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length()>45)
            throw new IllegalArgumentException("Fjalekalimi Nuk mund te jete kaq i gjate");
        this.password = password;
    }

    public boolean isNotification_on() {

        return notification_on;
    }

    public void setNotification_on(boolean notification_on) {
        this.notification_on = notification_on;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
