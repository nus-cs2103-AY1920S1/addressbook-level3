package dukecooks.logic.parser.dashboard;

import java.util.Arrays;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.dashboard.FindTaskCommand;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.dashboard.components.DashboardNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindRecipeCommand object
 */
public class FindTaskCommandParser implements Parser<FindTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTaskCommand
     * and returns a FindTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTaskCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindTaskCommand(new DashboardNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
