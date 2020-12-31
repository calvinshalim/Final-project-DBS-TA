package sample;


import java.sql.DriverManager;
import java.sql.Connection;


public class Databaseconnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String dbname = "u448942690_project";
        String user = "u448942690_rowin";
        String password = "killuaYUM123";
        String url = "jdbc:mysql://milenovaldo.me/u448942690_project";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }
}

