package com.microchip.parallelsearch.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The SearchException class represents an exception specific to search operations.
 * It is used to indicate errors or exceptional conditions that occur during search processes.
 *
 * @author Ashish Kumar Mahuri
 */
public class SearchException extends Exception {

    private static final Logger logger = LogManager.getLogger(SearchException.class);

    /**
     * Constructs a SearchException with the specified error message.
     *
     * @param message The error message.
     */
    public SearchException(String message) {
        super(message);
    }

    /**
     * Constructs a SearchException with the specified error message and cause.
     *
     * @param message The error message.
     * @param cause   The cause of the exception.
     */
    public SearchException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }
}
