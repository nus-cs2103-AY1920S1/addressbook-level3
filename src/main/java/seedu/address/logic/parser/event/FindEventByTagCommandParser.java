package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.event.FindEventByTagCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventTagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindEventByTagCommand object
 */
public class FindEventByTagCommandParser implements Parser<FindEventByTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindEventByTagCommand
     * and returns a FindEventByTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindEventByTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventByTagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new FindEventByTagCommand(new EventTagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}

