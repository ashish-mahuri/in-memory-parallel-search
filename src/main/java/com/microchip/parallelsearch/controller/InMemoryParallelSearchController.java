package com.microchip.parallelsearch.controller;

import com.microchip.parallelsearch.exception.SearchException;
import com.microchip.parallelsearch.model.Combinations;
import com.microchip.parallelsearch.model.InMemoryParallelSearchEngine;

import com.microchip.parallelsearch.util.DisplayErrorInUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The InMemoryParallelSearchController class for the main view.
 * Handles user interactions and communicates with the InMemoryParallelSearchEngine.
 * It initializes the UI elements and handles user actions.
 * The search functionality is triggered by the search() method.
 * The initialize() method is called when the view is loaded to set up the initial state.
 * It uses the InMemoryParallelSearchEngine model to perform the search operation.
 *
 * @author Ashish Kumar Mahuri
 */
public class InMemoryParallelSearchController {

    private static final Logger logger = LogManager.getLogger(InMemoryParallelSearchController.class);

    @FXML
    private TextField searchTextField;

    @FXML
    private ListView<String> foundStringsListView;

    @FXML
    private Label statusLabel;

    private InMemoryParallelSearchEngine searchEngine;

    /**
     * Initializes the controller.
     * Sets up the search engine and populates the UI with random strings before search.
     * This method is called when the view is loaded.
     * It sets up the search engine model and binds the UI elements to the model properties.
     * It populates the UI with random strings to simulate initial data.
     * It handles any exceptions during initialization and logs errors.
     */
    @FXML
    private void initialize() {
        try {
            logger.info("Entering into: initialize()");
            // Initialize the parallel search model
            searchEngine = new InMemoryParallelSearchEngine();

            // Bind the UI elements to the model properties
            foundStringsListView.setItems(searchEngine.getFoundStrings());
            statusLabel.textProperty().bind(searchEngine.getStatusProperty());

            // Load all values in the UI before search in random order
            String[] allCombinations = Combinations.ALL_COMBINATIONS.toArray(new String[0]);
            List<String> randomOrderList = Arrays.asList(allCombinations);
            Collections.shuffle(randomOrderList);
            foundStringsListView.getItems().addAll(randomOrderList);

        } catch (Exception exception) {
            logger.error("Error occurred during initialization: {}", exception.getMessage());
            DisplayErrorInUI.displayErrorInUI("Error occurred during initialization: " + exception.getMessage());
        } finally {
            logger.info("Exiting from: initialize()");
        }

    }

    /**
     * Performs the search based on the entered text.
     * This method is called when the user triggers the search action.
     * It retrieves the search text from the input field and triggers the search operation in the search engine.
     * It handles any exceptions during the search process and logs errors.
     */
    @FXML
    private void search() {
        try {
            logger.info("Entering into: search()");

            // Set the number of CPUs to be used
            searchEngine.setNumCPUs(Runtime.getRuntime().availableProcessors());

            // Perform the search using the entered text
            String searchText = searchTextField.getText();
            searchEngine.search(searchText);

        } catch (SearchException searchException) {
            logger.error("Search exception occurred: {}", searchException.getMessage());
            DisplayErrorInUI.displayErrorInUI("Search exception occurred: " + searchException.getCause().getMessage());
        } catch (Exception exception) {
            logger.error("Error occurred during search: {}", exception.getMessage());
            DisplayErrorInUI.displayErrorInUI("Error occurred during search: " + exception.getMessage());
        } finally {
            logger.info("Exiting from: search()");
        }
    }
}
