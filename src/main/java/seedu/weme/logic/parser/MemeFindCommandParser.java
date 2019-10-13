package seedu.weme.logic.parser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.weme.logic.commands.MemeFindCommand;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.model.meme.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new MemeFindCommand object
 */
public class MemeFindCommandParser implements Parser<MemeFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MemeFindCommand
     * and returns a MemeFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MemeFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemeFindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new MemeFindCommand(new TagContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
