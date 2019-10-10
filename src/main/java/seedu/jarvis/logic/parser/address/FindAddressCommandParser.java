package seedu.jarvis.logic.parser.address;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.jarvis.logic.commands.address.FindAddressCommand;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.address.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindAddressCommand object
 */
public class FindAddressCommandParser implements Parser<FindAddressCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindAddressCommand
     * and returns a FindAddressCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindAddressCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAddressCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindAddressCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
