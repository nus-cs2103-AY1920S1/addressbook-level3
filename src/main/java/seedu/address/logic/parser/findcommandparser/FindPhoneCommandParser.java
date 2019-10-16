package seedu.address.logic.parser.findcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.findcommand.FindPhoneCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.phone.predicates.BrandContainsKeywordsPredicate;
import seedu.address.model.phone.predicates.CapacityContainsKeywordsPredicate;
import seedu.address.model.phone.predicates.ColourContainsKeywordsPredicate;
import seedu.address.model.phone.predicates.CostContainsKeywordsPredicate;
import seedu.address.model.phone.predicates.IdentityNumberContainsKeywordsPredicate;
import seedu.address.model.phone.predicates.PhoneNameContainsKeywordsPredicate;
import seedu.address.model.phone.predicates.SerialNumberContainsKeywordsPredicate;

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
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindPhoneCommand(
                new PhoneNameContainsKeywordsPredicate(Arrays.asList(nameKeywords))
                        .or(new BrandContainsKeywordsPredicate(Arrays.asList(nameKeywords)))
                        .or(new CapacityContainsKeywordsPredicate(Arrays.asList(nameKeywords)))
                        .or(new ColourContainsKeywordsPredicate(Arrays.asList(nameKeywords)))
                        .or(new CostContainsKeywordsPredicate(Arrays.asList(nameKeywords)))
                        .or(new IdentityNumberContainsKeywordsPredicate(Arrays.asList(nameKeywords)))
                        .or(new SerialNumberContainsKeywordsPredicate(Arrays.asList(nameKeywords))));
    }

}
