package com.microchip.parallelsearch.util;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The DisplayErrorInUI class provides a utility method to display an error message in the UI.
 * It utilizes the JavaFX Platform.runLater() method to ensure the error message is displayed on the UI thread.
 *
 * @author Ashish Kumar Mahuri
 */
public class DisplayErrorInUI {

    private static final Logger logger = LogManager.getLogger(DisplayErrorInUI.class);

    /**
     * Displays the given error message in the UI.
     *
     * @param errorMessage The error message to display.
     */
    public static void displayErrorInUI(String errorMessage) {
        logger.log(Level.INFO, "Entering into: displayErrorInUI()");
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(errorMessage);
            alert.showAndWait();
        });
    }
}
