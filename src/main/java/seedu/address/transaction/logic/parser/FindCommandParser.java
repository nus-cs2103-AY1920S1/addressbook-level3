package seedu.address.transaction.logic.parser;

import java.util.Arrays;
import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.transaction.logic.commands.FindCommand;
import seedu.address.transaction.logic.parser.exception.ParseException;
import seedu.address.transaction.model.transaction.TransactionContainsKeywordsPredicate;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements IndependentCommandParser {
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            logger.info("There is no valid keywords after trimming all trailing whitespaces.");
            throw new ParseException(
                    TransactionMessages.MESSAGE_INVALID_FIND_COMMAND_FORMAT);
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        assert nameKeywords.length >= 1 : "No keywords";
        return new FindCommand(new TransactionContainsKeywordsPredicate((Arrays.asList(nameKeywords))));
    }
}
