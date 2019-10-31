package seedu.ichifund.logic.parser.budget;

import java.util.Arrays;
import java.util.Collections;

import seedu.ichifund.logic.commands.budget.FindBudgetCommand;
import seedu.ichifund.logic.parser.Parser;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.budget.BudgetDescriptionPredicate;

/**
 * Parses input arguments and creates a new FindBudgetCommand object
 */
public class FindBudgetCommandParser implements Parser<FindBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindBudgetCommand
     * and returns a FindBudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindBudgetCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new FindBudgetCommand(new BudgetDescriptionPredicate(Collections.emptyList()));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindBudgetCommand(new BudgetDescriptionPredicate(Arrays.asList(nameKeywords)));
    }

}
