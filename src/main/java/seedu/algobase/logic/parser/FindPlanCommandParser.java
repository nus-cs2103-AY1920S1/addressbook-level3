package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.algobase.logic.commands.FindPlanCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.plan.NameContainsKeywordsPredicate;



/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindPlanCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPlanCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPlanCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindPlanCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
