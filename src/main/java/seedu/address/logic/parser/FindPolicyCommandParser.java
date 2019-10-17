package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicyNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPolicyCommand object
 */
public class FindPolicyCommandParser implements Parser<FindPolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPolicyCommand
     * and returns a FindPolicyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPolicyCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPolicyCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindPolicyCommand(new PolicyNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
