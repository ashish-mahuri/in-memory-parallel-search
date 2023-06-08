package com.microchip.parallelsearch.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.microchip.parallelsearch.util.DisplayErrorInUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The StringDataWriter class responsible for writing string data to JSON format.
 *
 * @author Ashish Kumar Mahuri
 */
public class StringDataWriter {

    private static final Logger logger = LogManager.getLogger(StringDataWriter.class);

    /**
     * Saves the list of strings in JSON format to the specified file.
     *
     * @param strings  The list of strings to be saved
     * @param filePath The path to the JSON file
     */
    public static void saveStringsInJSON(List<String> strings, String filePath) {
        try {
            logger.info("Entering into: saveStringInJSON()");
            ObjectMapper objectMapper = new ObjectMapper();

            // Write the list of strings to JSON file using the ObjectMapper
            objectMapper.writeValue(new File(filePath), strings);

            logger.info("Strings saved to JSON file: {}", filePath);
        } catch (IOException ioException) {
            logger.error("Error occurred while saving strings to JSON file: {}", ioException.getMessage());
            DisplayErrorInUI.displayErrorInUI("Error occured while saving the data into JSON file" + ioException.getMessage());
        } finally {
            logger.info("Exiting from: saveStringInJSON()");
        }
    }
}
