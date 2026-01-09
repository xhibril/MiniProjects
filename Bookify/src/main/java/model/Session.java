package model;

public class Session {
    private static String usernameLoggedIn;

    public static void setUserLoggedIn(String username) {
        usernameLoggedIn = username;
    }

    public static String getUsernameLoggedIn() {
        return usernameLoggedIn;
    }
}
