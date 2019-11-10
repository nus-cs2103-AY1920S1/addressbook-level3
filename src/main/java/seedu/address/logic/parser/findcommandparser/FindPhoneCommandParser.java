package seedu.address.logic.parser.findcommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.findcommand.FindPhoneCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.phone.predicates.PhoneContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPhoneCommand object
 */
public class FindPhoneCommandParser implements Parser<FindPhoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPhoneCommand
     * and returns a FindPhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPhoneCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");

        return new FindPhoneCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));
    }

}
