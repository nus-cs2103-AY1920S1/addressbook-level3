package seedu.address.commons.util;

import java.util.List;
import java.util.SortedSet;

import seedu.address.commons.core.index.Index;

/**
 * A class containing utility methods for {@code Provider}s.
 */
public class ProviderUtil {

    /**
     * Populates a given {@code SortedSet} from data in a {@code dataList}.
     * @param values The set to populate.
     * @param dataList A data source.
     */
    public static void populateValuesWithIndexes(SortedSet<String> values, List<?> dataList) {
        if (!dataList.isEmpty()) {
            values.add(String.valueOf(1));
            values.add(String.valueOf(dataList.size()));
        }
    }

    /**
     * Checks if a given input string has preamble.
     * @return True if input has preamble, as defined by command, false otherwise.
     */
    public static boolean hasCompletePreamble(String input) {
        return input.stripLeading().contains(" ");
    }

    /**
     * Checks if a given {@code Index} is within the bounds of a list.
     * @param index Index to check.
     * @param dataList List to conform to.
     * @return True if valid, false otherwise.
     */
    public static boolean isValidIndex(Index index, List<?> dataList) {
        return index.getOneBased() >= 1 && index.getOneBased() <= dataList.size();
    }

    /**
     * Extracts the preamble of an input string.
     * @return A substring of the input defined as the preamble.
     */
    public static String extractPreamble(String input) {
        String trimmedInput = input.stripLeading();
        int secondSpace = trimmedInput.indexOf(" ");
        if (secondSpace < 0) {
            throw new IllegalArgumentException();
        }
        return input.stripLeading().substring(0, secondSpace);
    }

}
