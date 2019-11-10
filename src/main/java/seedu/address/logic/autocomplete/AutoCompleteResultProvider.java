package seedu.address.logic.autocomplete;

/**
 * Classes that implement this interface can provide a final {@code AutoCompleteResult} for autocomplete suggestions.
 */
public interface AutoCompleteResultProvider {
    /**
     * Processes an input string and returns suggestions for autocompletion.
     * @param input A user input string.
     * @return An {@code AutoCompleteResult} containing possible autocomplete values.
     */
    AutoCompleteResult process(String input);

}
