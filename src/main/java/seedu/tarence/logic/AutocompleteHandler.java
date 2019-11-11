package seedu.tarence.logic;

import org.apache.commons.lang3.StringUtils;

import seedu.tarence.logic.parser.PartialInput;
import seedu.tarence.logic.parser.PartialInputParser;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.Model;

/**
 * Handles autocompletion of partial inputs from user.
 */
public class AutocompleteHandler {

    private static final String ERROR_AUTOCOMPLETE_DATA_NOT_FOUND = "No autofill data found.";

    private Model model;

    public AutocompleteHandler(Model model) {
        this.model = model;
    }

    /**
     * Main method for handling autocomplete requests from user.
     *
     * @param input Partial input string from user.
     * @return Completed string to be filled into the autocomplete command box.
     * @throws ParseException if the users tries to autocomplete an invalid/unsupported field.
     * @throws IndexOutOfBoundsException if no autocomplete data is found for the field
     */
    public String handle(String input) throws ParseException, IndexOutOfBoundsException {
        PartialInput partialInput;
        try {
            partialInput = PartialInputParser.parse(input, model);
            model.storeSuggestedCompletions(partialInput);
            return StringUtils.removeStart(partialInput.getCompletions().get(0).toLowerCase(),
                    partialInput.getLastArgument().toLowerCase());
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    public String getNextSuggestion() throws IndexOutOfBoundsException {
        PartialInput partialInput = model.getSuggestedCompletions();
        if (partialInput == null || partialInput.getCompletions().size() == 0) {
            throw new IndexOutOfBoundsException("Dummy message.");
        }

        // simulate wraparound of completions list by removing first item and placing at back of list
        partialInput.getCompletions().add(partialInput.getCompletions().remove(0));
        return StringUtils.removeStart(partialInput.getCompletions().get(0).toLowerCase(),
                partialInput.getLastArgument().toLowerCase());
    }

}
