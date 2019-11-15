package dukecooks.logic.parser.mealplan;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.mealplan.ViewMealPlanCommand;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewMealPlanCommand object
 */
public class ViewMealPlanCommandParser implements Parser<ViewMealPlanCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewMealPlanCommand
     * and returns a ViewMealPlanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewMealPlanCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        Index targetIndex = ParserUtil.parseIndex(trimmedArgs);

        return new ViewMealPlanCommand(targetIndex);
    }

}
