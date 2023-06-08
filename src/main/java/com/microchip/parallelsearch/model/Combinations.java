package com.microchip.parallelsearch.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for generating all combinations of 4 capital letters.
 * It provides a list of all possible combinations and a method to generate them.
 *
 * @author Ashish Kumar Mahuri
 */
public class Combinations {

    private static final Logger logger = LogManager.getLogger(Combinations.class);
    private Combinations() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * The list of all possible combinations of 4 capital letters.
     */
    public static final List<String> ALL_COMBINATIONS = generateCombinations();

    /**
     * Generates all possible combinations of 4 capital letters.
     *
     * @return The list of all combinations.
     */
    private static List<String> generateCombinations() {
        logger.info("Entering into: generateCombinations()");
        List<String> combinations = new ArrayList<>();
        int totalCombinations = (int) Math.pow(26, 4);

        for (int i = 0; i < totalCombinations; i++) {
            char[] combination = new char[4];
            int remaining = i;

            for (int j = 0; j < 4; j++) {
                int index = remaining % 26;
                char ch = (char) ('A' + index);
                combination[3 - j] = ch;
                remaining /= 26;
            }

            combinations.add(String.valueOf(combination));
        }

        return combinations;
    }
}
