package seedu.address.logic.parser.statistics;

import java.util.HashMap;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents parser type for processing Statistics data.
 */
public interface DataParser {

    HashMap<String, HashMap<String, Double>> parseFile(String filePath) throws ParseException;

}
