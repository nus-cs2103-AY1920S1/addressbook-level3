package seedu.address.logic;

public interface AutoCompleteResultProvider {
    /**
     * Processes an input string and returns suggestions for autocompletion.
     * @param input A user input string.
     * @return An {@code AutoCompleteResult} containing possible autocomplete values.
     */
    AutoCompleteResult process(String input);

}
