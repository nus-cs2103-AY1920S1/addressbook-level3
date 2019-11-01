package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.ListClassCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ClassIdContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new ListClassCommand object
 */
public class ListClassCommandParser implements Parser<ListClassCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListClassCommand
     * and returns a ListClassCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListClassCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListClassCommand.MESSAGE_USAGE));
        }

        String[] classKeywords = trimmedArgs.split("\\s+");

        return new ListClassCommand(new ClassIdContainsKeywordsPredicate(Arrays.asList(classKeywords)));
    }

}
