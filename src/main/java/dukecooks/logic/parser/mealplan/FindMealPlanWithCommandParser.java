package dukecooks.logic.parser.mealplan;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import dukecooks.logic.commands.mealplan.FindMealPlanWithCommand;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.mealplan.components.MealPlanRecipesContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindMealPlanWithCommand object
 */
public class FindMealPlanWithCommandParser implements Parser<FindMealPlanWithCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindMealPlanWithCommand
     * and returns a FindMealPlanWithCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindMealPlanWithCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMealPlanWithCommand.MESSAGE_USAGE));
        }

        String nameKeywords = String.join(" ", trimmedArgs.split("\\s+"));

        return new FindMealPlanWithCommand(new MealPlanRecipesContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
