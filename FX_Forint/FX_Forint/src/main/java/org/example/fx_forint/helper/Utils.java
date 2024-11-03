package org.example.fx_forint.helper;

import javafx.scene.control.Alert;

public class Utils {

    /**
     * Visszaadja hogy a megadott érték integer-e
     * @param str
     * @return true or false
     */
    public static boolean isInteger(String str) {
        if (str != null && str.length() > 0) {
            try {
                int numb = Integer.parseInt(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Visszaadja hogy a megadott érték double-e
     * @param str
     * @return true or false
     */
    public static boolean isDouble(String str) {
        if (str != null && str.length() > 0) {
            str = str.replace(",",".");
            try {
                Double numb = Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * felugró ablak
     * @param type Alert.AlertType típus
     * @param message Üzenet szövege
     * @param title Ablak címe
     */
    public static void showMessage(Alert.AlertType type, String message, String title) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
