package com.microchip.parallelsearch;

import com.microchip.parallelsearch.util.DisplayErrorInUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The InMemoryParallelSearchApplication class that serves as the entry point of the application.
 * This class extends the JavaFX Application class.
 * The main entry point for the JavaFX application is the start() method.
 * It loads the main FXML file and displays the application window.
 * It also includes a main() method to launch the JavaFX application.
 *
 * @author Ashish Kumar Mahuri
 */
public class InMemoryParallelSearchApplication extends Application {

    private static final Logger logger = LogManager.getLogger(InMemoryParallelSearchApplication.class);

    /**
     * The start() method is the main entry point for the JavaFX application.
     * It loads the main FXML file, sets up the primary stage, and displays the application window.
     *
     * @param primaryStage the primary stage for the application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            logger.info("Entering into: start()");
            // Load the main FXML file
            FXMLLoader loader = new FXMLLoader(InMemoryParallelSearchApplication.class.getResource("in-memory-parallel-search.fxml"));
            Pane rootPane = loader.load();

            primaryStage.setScene(new Scene(rootPane));
            primaryStage.setTitle("In Memory Parallel Search");
            primaryStage.setMinWidth(500.0d);
            primaryStage.setMinHeight(200.0d);
            primaryStage.show();
        } catch (IOException ioException) {
            logger.error("Unable to load fxml resources", ioException);
            // Handle the exception and display an error message in the UI
            DisplayErrorInUI.displayErrorInUI("Unable to load resources: " + ioException.getMessage());
        } catch (Exception exception) {
            logger.error("Error occurred while searching the data", exception);
            // Handle the exception and display an error message in the UI
            DisplayErrorInUI.displayErrorInUI("Error occurred while loading the resource: " + exception.getMessage());
        } finally {
            logger.info("Exiting from: start()");
        }
    }

    /**
     * The main() method that launches the JavaFX application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        logger.info("launching the application");
        launch(args);
    }
}
