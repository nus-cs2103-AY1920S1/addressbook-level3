package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.password.PasswordDescription.isValidDescription;

import java.util.Arrays;

import seedu.address.logic.commands.FindPasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.password.DescriptionContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindPasswordCommand object
 */
public class FindPasswordCommandParser implements Parser<FindPasswordCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPasswordCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPasswordCommand.MESSAGE_USAGE));
        }

        if (!isValidDescription(trimmedArgs)) {
            throw new ParseException(FindPasswordCommand.MESSAGE_USAGE);
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindPasswordCommand(new DescriptionContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
