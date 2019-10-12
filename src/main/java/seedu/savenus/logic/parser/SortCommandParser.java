package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.savenus.logic.commands.SortCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given String and returns a SortCommand object for simple execution.
     * @throws ParseException if the String contains duplicate or invalid fields.
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        if (!areFieldsValid(nameKeywords)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand((Arrays.asList(nameKeywords)));
    }

    /**
     * Checks if the array of fields contain duplicate of invalid values.
     * @param keywords the array of fields.
     * @return true if the fields do not contain duplicate of invalid fields. False if otherwise.
     */
    public boolean areFieldsValid(String[] keywords) {
        Set<String> noDuplicateFields = new HashSet<String>();
        for (int i = 0; i < keywords.length; i++) {
            String field = keywords[i];
            if (!isValidField(field) || !noDuplicateFields.add(field)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the field is valid or not.
     * @param field the field.
     * @return true if the field is valid. False if otherwise.
     */
    public boolean isValidField(String field) {
        return field.equals("NAME")
                || field.equals("PRICE")
                || field.equals("CATEGORY")
                || field.equals("DESCRIPTION")
                || field.equals("LOCATION")
                || field.equals("OPENING_HOURS")
                || field.equals("RESTRICTIONS");
    }
}
