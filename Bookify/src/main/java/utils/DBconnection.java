package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
   private static Connection conn;

    public static void connectToDatabase() throws SQLException {
        String url  = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASSWORD");
         conn = DriverManager.getConnection(url, user, pass);
    }
    public static Connection getConnection(){ return conn; }
}
