package org.example.fx_forint.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class databaseHelper {
    private static String url;

    public static void setDbFilePath(String dbFilePath) {
        url = dbFilePath;
    }

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("létrejött db kapcsolat");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void insertData(String sql) {
        try (Connection connection = connect();
             Statement stmt = connection.createStatement()) {
            if (connection != null) {
                stmt.executeUpdate(sql);
                System.out.println("Inserted");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
