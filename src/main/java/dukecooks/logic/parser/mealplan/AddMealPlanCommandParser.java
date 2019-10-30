package dukecooks.logic.parser.mealplan;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY1;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY2;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY3;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY4;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY5;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY6;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY7;
import static dukecooks.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.stream.Stream;

import dukecooks.logic.commands.mealplan.AddMealPlanCommand;
import dukecooks.logic.parser.ArgumentMultimap;
import dukecooks.logic.parser.ArgumentTokenizer;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.Prefix;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.components.MealPlanName;
import dukecooks.model.recipe.components.RecipeName;

/**
 * Parses input arguments and creates a new AddMealPlanCommand object
 */
public class AddMealPlanCommandParser implements Parser<AddMealPlanCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMealPlanCommand
     * and returns an AddMealPlanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMealPlanCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DAY1, PREFIX_DAY2, PREFIX_DAY3, PREFIX_DAY4,
                        PREFIX_DAY5, PREFIX_DAY6, PREFIX_DAY7);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMealPlanCommand.MESSAGE_USAGE));
        }

        MealPlanName name = ParserUtil.parseMealPlanName(argMultimap.getValue(PREFIX_NAME).get());
        List<RecipeName> day1 = ParserUtil.parseRecipes(argMultimap.getAllValues(PREFIX_DAY1));
        List<RecipeName> day2 = ParserUtil.parseRecipes(argMultimap.getAllValues(PREFIX_DAY2));
        List<RecipeName> day3 = ParserUtil.parseRecipes(argMultimap.getAllValues(PREFIX_DAY3));
        List<RecipeName> day4 = ParserUtil.parseRecipes(argMultimap.getAllValues(PREFIX_DAY4));
        List<RecipeName> day5 = ParserUtil.parseRecipes(argMultimap.getAllValues(PREFIX_DAY5));
        List<RecipeName> day6 = ParserUtil.parseRecipes(argMultimap.getAllValues(PREFIX_DAY6));
        List<RecipeName> day7 = ParserUtil.parseRecipes(argMultimap.getAllValues(PREFIX_DAY7));

        MealPlan mealPlan = new MealPlan(name);

        return new AddMealPlanCommand(mealPlan, day1, day2, day3, day4, day5, day6, day7);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
