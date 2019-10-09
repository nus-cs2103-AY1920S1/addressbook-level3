package seedu.address.transaction.logic;

import java.util.Arrays;
import seedu.address.transaction.commands.FindCommand;
import seedu.address.transaction.logic.exception.ParseException;
import seedu.address.transaction.model.TransactionContainsKeywordsPredicate;
import seedu.address.transaction.ui.TransactionMessages;

public class FindCommandParser {

    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    TransactionMessages.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new TransactionContainsKeywordsPredicate((Arrays.asList(nameKeywords))));
    }
}
