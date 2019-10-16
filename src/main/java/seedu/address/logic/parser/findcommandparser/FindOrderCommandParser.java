package seedu.address.logic.parser.findcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.findcommand.FindOrderCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.predicates.CustomerContainsKeywordsPredicate;
import seedu.address.model.order.predicates.IdContainsKeywordsPredicate;
import seedu.address.model.order.predicates.PhoneContainsKeywordsPredicate;
import seedu.address.model.order.predicates.PriceContainsKeywordsPredicate;
import seedu.address.model.order.predicates.StatusContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindOrderCommand object
 */
public class FindOrderCommandParser implements Parser<FindOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindOrderCommand
     * and returns a FindOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindOrderCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindOrderCommand(
                new IdContainsKeywordsPredicate(Arrays.asList(nameKeywords))
                        .or(new PriceContainsKeywordsPredicate(Arrays.asList(nameKeywords)))
                        .or(new StatusContainsKeywordsPredicate(Arrays.asList(nameKeywords)))
                        .or(new CustomerContainsKeywordsPredicate(Arrays.asList(nameKeywords)))
                        .or(new PhoneContainsKeywordsPredicate(Arrays.asList(nameKeywords))));
    }

}
