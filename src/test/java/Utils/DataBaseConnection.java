package Utils;

import java.sql.*;

public class DataBaseConnection {

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:postgresql://34.93.33.184:5432/akku-dev-v2-database?currentSchema=akku-dev-v2-database";
    private static final String USERNAME = "akkuadmin";
    private static final String PASSWORD = "Cloudnow@1212";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }



}
