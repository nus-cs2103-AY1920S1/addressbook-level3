package seedu.jarvis.logic.parser.finance;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.jarvis.logic.commands.finance.FindPurchaseCommand;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.finance.PurchaseNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPurchaseCommand object
 */
public class FindPurchaseCommandParser implements Parser<FindPurchaseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPurchaseCommand
     * and returns a FindCcaCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindPurchaseCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPurchaseCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindPurchaseCommand(new PurchaseNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
