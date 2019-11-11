package seedu.address.commons.util;

import seedu.address.commons.core.index.Index;

import java.util.List;
import java.util.SortedSet;

public class ProviderUtil {

    public static void populateValuesWithIndexes(SortedSet<String> values, List<?> dataList) {
        if (!dataList.isEmpty()) {
            values.add(String.valueOf(1));
            values.add(String.valueOf(dataList.size()));
        }
    }

    public static boolean hasCompletePreamble(String input) {
        return input.stripLeading().contains(" ");
    }

    public static boolean isValidIndex(Index index, List<?> dataList) {
        return index.getOneBased() >= 1 && index.getOneBased() <= dataList.size();
    }

    public static String extractPreamble(String input) {
        String trimmedInput = input.stripLeading();
        int secondSpace = trimmedInput.indexOf(" ");
        if (secondSpace < 0) {
            throw new IllegalArgumentException();
        }
        return input.stripLeading().substring(0, secondSpace);
    }

}
