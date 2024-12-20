package org.example.fx_forint.helper;

import org.example.fx_forint.models.Deviza;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class databaseHelper {
    private static String url;

    public static void setDbFilePath(String dbFilePath) {
        url = dbFilePath;
    }

    /**
     * Adatbázis csatlakozás
     * @return
     */
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

    /**
     * Adatbázis insert a paraméterben megadott sql-el
     * @param sql
     */
    public static void executeSql(String sql) {
        try (Connection connection = connect();
             Statement stmt = connection.createStatement()) {
            if (connection != null) {
                stmt.executeUpdate(sql);
                System.out.println("Successful");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Generikus metódus, a paraméterben várt modell alapján rakjuk össze a választ
     * @param sql lekérdezés
     * @param modelT modell
     * @return modell típusú lista
     * @param <T>
     */
    public static <T> List<T> getRecords(String sql, Class<T> modelT) {
        List<T> list = new ArrayList<>();

        //String sql = "select * from " + tableName;

        try (Connection connection = connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (connection != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    T model = modelT.getDeclaredConstructor().newInstance();

                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = rs.getObject(i);

                        try {
                            Field field = modelT.getDeclaredField(columnName);
                            field.setAccessible(true);
                            //System.out.println("A " + field.getName() + " mezo tipusa: " + field.getType());
                            if (field.getType() == java.time.LocalDate.class && value != null) {
                                value = LocalDate.parse(value.toString(), formatter);
                            }

                            field.set(model, value);
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            System.out.println("A mező nem található. " + e.getMessage());
                        }
                    }
                    list.add(model);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    /**
     * Az elérhető devizanemek adatbázisba mentése
     * @param currencies Deviza lista
     */
    public static void saveCurrency(List<Deviza> currencies) {
        String sql = "INSERT into deviza (devizanem) select (?) where not exists (select 1 from deviza where devizanem=?)";

        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (Deviza currency : currencies) {
                stmt.setString(1, currency.getDevizanem());
                stmt.setString(2, currency.getDevizanem());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
