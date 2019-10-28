package dukecooks.logic.parser.mealplan;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import dukecooks.logic.commands.mealplan.FindMealPlanCommand;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.mealplan.components.MealPlanNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindMealPlanCommand object
 */
public class FindMealPlanCommandParser implements Parser<FindMealPlanCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindMealPlanCommand
     * and returns a FindMealPlanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindMealPlanCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMealPlanCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindMealPlanCommand(new MealPlanNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
