package seedu.jarvis.logic.parser.cca;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.jarvis.logic.commands.cca.FindCcaCommand;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.cca.CcaNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCcaCommand object
 */
public class FindCcaCommandParser implements Parser<FindCcaCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCcaCommand
     * and returns a FindCcaCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCcaCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCcaCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCcaCommand(new CcaNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
