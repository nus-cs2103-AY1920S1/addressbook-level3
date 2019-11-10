package seedu.address.logic.autocomplete;

import java.util.SortedSet;

/**
 * Classes that implement this interface can provide values for autocomplete suggestion.
 */
public interface AutoCompleteValueProvider {

    SortedSet<String> getValues();

}
