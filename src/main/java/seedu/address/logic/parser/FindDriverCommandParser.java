package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindDriverCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindDriverCommandParser implements Parser<FindDriverCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindDriverCommand
     * and returns a FindDriverCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindDriverCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDriverCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindDriverCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
