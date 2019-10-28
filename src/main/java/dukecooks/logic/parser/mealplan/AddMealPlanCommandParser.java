package dukecooks.logic.parser.mealplan;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_NAME;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMealPlanCommand.MESSAGE_USAGE));
        }

        MealPlanName name = ParserUtil.parseMealPlanName(argMultimap.getValue(PREFIX_NAME).get());

        MealPlan mealPlan = new MealPlan(name);

        return new AddMealPlanCommand(mealPlan);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
