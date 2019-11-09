package seedu.address.logic;

import java.util.Collections;

/**
 * Represents a {@code AutoCompleteResultProvider} that returns no results.
 */
public class EmptyProvider implements AutoCompleteResultProvider {

    private static EmptyProvider theOne;

    private EmptyProvider() {}

    public static EmptyProvider getInstance() {
        if (theOne == null) {
            theOne = new EmptyProvider();
        }
        return theOne;
    }

    @Override
    public AutoCompleteResult process(String input) {
        return new AutoCompleteResult(Collections.emptySortedSet(), "");
    }

}
