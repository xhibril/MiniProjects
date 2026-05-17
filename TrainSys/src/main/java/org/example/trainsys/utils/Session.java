package org.example.trainsys.utils;

public class Session {
    private static Long userId;
    private static String role;

    public static void setUser(Long id, String userRole) {
        userId = id;
        role = userRole;
    }

    public static Long getUserId() {
        return userId;
    }

    public static String getRole() {
        return role;
    }
}