package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.SortedSet;

/**
 * Represents a result from autocompletion comparison, containing a set of possible values and a string to match them.
 */
public class AutoCompleteResult {

    private final SortedSet<String> values;
    private final String stringToCompare;

    public AutoCompleteResult(SortedSet<String> values, String stringToCompare) {
        requireNonNull(stringToCompare);
        this.values = values;
        this.stringToCompare = stringToCompare;
    }

    public SortedSet<String> getValues() {
        return values;
    }

    public String getStringToCompare() {
        return stringToCompare;
    }

}
