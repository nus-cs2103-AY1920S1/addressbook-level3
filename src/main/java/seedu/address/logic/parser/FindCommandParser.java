package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.expense.FindExpenseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expense.DescriptionContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindExpenseCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindExpenseCommand.MESSAGE_USAGE));
        }

        String[] descriptionKeywords = trimmedArgs.split("\\s+");

        return new FindExpenseCommand(new DescriptionContainsKeywordsPredicate(Arrays.asList(descriptionKeywords)));
    }

}
