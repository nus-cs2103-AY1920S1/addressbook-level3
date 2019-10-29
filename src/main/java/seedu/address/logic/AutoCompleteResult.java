package seedu.address.logic;

import java.util.SortedSet;

import static java.util.Objects.requireNonNull;

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
