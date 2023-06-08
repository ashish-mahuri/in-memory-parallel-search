package com.microchip.parallelsearch.model;

import com.microchip.parallelsearch.exception.SearchException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * The InMemoryParallelSearchEngine class responsible for performing the in-memory parallel search.
 * This class utilizes a ForkJoinPool to divide the search operation into parallel tasks.
 * The search is performed on a list of combinations and matching results are stored in an observable list.
 * The class provides methods to get the list of found strings based on searched text and the status of the search operation.
 * It also allows setting the number of CPUs to be used for parallel processing.
 *
 * @author Ashish Kumar Mahuri
 */
public class InMemoryParallelSearchEngine {

    private static final Logger logger = LogManager.getLogger(InMemoryParallelSearchEngine.class);
    private ObservableList<String> foundStrings;
    private ObservableList<String> searchResults; // Separate list for storing search results during a search
    private StringProperty statusProperty;
    private int numCPUs; //number of available CPU


    /**
     * Constructs an instance of the InMemoryParallelSearchEngine.
     * Initializes the observable lists for found strings and search results.
     * Retrieves the number of available CPUs and logs the information.
     */
    public InMemoryParallelSearchEngine() {
        foundStrings = FXCollections.observableArrayList();
        searchResults = FXCollections.observableArrayList();
        statusProperty = new SimpleStringProperty("Idle");
        numCPUs = Runtime.getRuntime().availableProcessors();
        logger.log(Level.INFO, "Number of available CPUs: {}", numCPUs);

    }

    /**
     * Gets the observable list of found strings.
     *
     * @return The observable list of found strings.
     */
    public ObservableList<String> getFoundStrings() {
        return foundStrings;
    }

    /**
     * Gets the status property.
     *
     * @return The status property.
     */
    public StringProperty getStatusProperty() {
        return statusProperty;
    }

    /**
     * Sets the number of CPUs to use for the parallel search.
     *
     * @param numCPUs The number of CPUs to use.
     * @throws IllegalArgumentException if the number of CPUs is invalid.
     */
    public void setNumCPUs(int numCPUs) {
        logger.info("Entering into: setNumCPUs()");
        try {
            if (numCPUs <= 0 || numCPUs > Runtime.getRuntime().availableProcessors()) {
                throw new IllegalArgumentException("Invalid number of CPUs");
            }
            this.numCPUs = numCPUs;
        } finally {
            logger.info("Exiting from: setNumCPUs()");
        }
    }

    /**
     * Performs the in-memory parallel search.
     *
     * @param searchText The search text to look for in the combinations.
     * @throws SearchException if error occur during the search.
     */
    public void search(String searchText) throws SearchException {
        try {
            logger.info("Entering into: search()");
            if (searchText == null || searchText.isEmpty() || searchText.length() > 4) {
                throw new SearchException("Search text cannot be null or empty and max length should be 4");
            }

            logger.log(Level.INFO, "Search started for: {}", searchText);

            // Clear previous search results
            searchResults.clear();

            Instant start = Instant.now();

            // Perform search in parallel
            try (ForkJoinPool forkJoinPool = new ForkJoinPool(numCPUs)) {
                SearchTask searchTask = new SearchTask(searchText, 0, Combinations.ALL_COMBINATIONS.size());
                forkJoinPool.invoke(searchTask);
            }

            Instant end = Instant.now();
            // Calculate the execution time for search
            Duration executionTime = Duration.between(start, end);
            String status = "Execution Time: " + executionTime.toMillis() + "ms";
            statusProperty.set(status);

            // Add the search results to the foundStrings list after the search is complete
            foundStrings.setAll(searchResults);

            // Save the found strings in different formats
            StringDataWriter.saveStringsInJSON(foundStrings, "found_strings.json");

            logger.log(Level.INFO, "Search completed for: {}", searchText);

        } catch (IllegalArgumentException | SearchException searchException) {
            logger.log(Level.ERROR, "Error occurred during search: {}", searchException.getMessage());
            throw new SearchException("Error occurred during search", searchException);
        } catch (Exception exception) {
            logger.log(Level.ERROR, "Generic Exception: {}", exception.getMessage());
            throw new SearchException("An unexpected error occurred during search", exception);
        } finally {
            logger.info("Exiting from: search()");
        }
    }

    /**
     * The SearchTask class represents a recursive action that performs the parallel search.
     * It divides the search operation into smaller tasks and utilizes the ForkJoin framework for parallel execution.
     */
    private class SearchTask extends RecursiveAction {

        private static final int THRESHOLD = 10000;
        private String searchText;
        private int start;
        private int end;

        /**
         * Instantiates a new Search task.
         *
         * @param searchText the search text
         * @param start      the start
         * @param end        the end
         */
        public SearchTask(String searchText, int start, int end) {
            this.searchText = searchText;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            try {
                logger.info("Entering into: compute()");
                if (end - start <= THRESHOLD) {
                    for (int i = start; i < end; i++) {
                        String combination = Combinations.ALL_COMBINATIONS.get(i);
                        if (combination.toLowerCase().contains(searchText.toLowerCase())) {
                            // Add the matching combination to the searchResults list
                            searchResults.add(combination);
                        }
                    }
                } else {
                    int mid = (start + end) / 2;
                    invokeAll(
                            new SearchTask(searchText, start, mid),
                            new SearchTask(searchText, mid, end)
                    );
                }
            } catch (Exception exception) {
                logger.log(Level.ERROR, "Error occurred while computing the search data: {}", exception.getMessage());
                } finally {
                logger.info("Exiting from: compute()");
            }
        }
    }
}
