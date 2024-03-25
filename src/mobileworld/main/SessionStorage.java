package mobileworld.main;

public class SessionStorage {

    private static SessionStorage INSTANCE;

    public static SessionStorage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SessionStorage();
        }
        return INSTANCE;
    }

    private SessionStorage() {

    }

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
