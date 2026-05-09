package org.example.taskflow.utils;

public class Session {
    private static Long userId;
    private static String user;

    public static void setUser(Long id, String username) {
        userId = id;
        user = username;

    }

    public static Long getUserId() {
        return userId;
    }

    public static String getUser(){ return user;}
}