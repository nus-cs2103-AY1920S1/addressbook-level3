package seedu.system.logic.parser.outofsession;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.system.logic.commands.outofsession.FindPersonCommand;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPersonCommand object.
 */
public class FindPersonCommandParser implements Parser<FindPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPersonCommand
     * and returns a FindPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPersonCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
        }
        String[] nameKeywords = trimmedArgs.split("\\s+");
        return new FindPersonCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
