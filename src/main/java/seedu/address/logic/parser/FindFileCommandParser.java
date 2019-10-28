package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindFileCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.file.FullPathContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindFileCommand object
 */
public class FindFileCommandParser implements Parser<FindFileCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindFileCommand
     * and returns a FindFileCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindFileCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindFileCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindFileCommand(new FullPathContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
