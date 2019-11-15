package dukecooks.logic.parser.recipe;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_CALORIES;
import static dukecooks.logic.parser.CliSyntax.PREFIX_CARBS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_FATS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_NAME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PROTEIN;

import java.util.Set;
import java.util.stream.Stream;

import dukecooks.logic.commands.recipe.AddRecipeCommand;
import dukecooks.logic.parser.ArgumentMultimap;
import dukecooks.logic.parser.ArgumentTokenizer;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.Prefix;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.recipe.components.Calories;
import dukecooks.model.recipe.components.Carbs;
import dukecooks.model.recipe.components.Fats;
import dukecooks.model.recipe.components.Ingredient;
import dukecooks.model.recipe.components.Protein;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.recipe.components.RecipeName;

/**
 * Parses input arguments and creates a new AddRecipeCommand object
 */
public class AddRecipeCommandParser implements Parser<AddRecipeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddRecipeCommand
     * and returns an AddRecipeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRecipeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INGREDIENT, PREFIX_CALORIES,
                        PREFIX_CARBS, PREFIX_FATS, PREFIX_PROTEIN);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_INGREDIENT, PREFIX_CALORIES,
                PREFIX_CARBS, PREFIX_FATS, PREFIX_PROTEIN)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRecipeCommand.MESSAGE_USAGE));
        }

        RecipeName name = ParserUtil.parseRecipeName(argMultimap.getValue(PREFIX_NAME).get());
        Set<Ingredient> ingredientList = ParserUtil.parseIngredients(argMultimap.getAllValues(PREFIX_INGREDIENT));
        Calories calories = ParserUtil.parseCalories(argMultimap.getValue(PREFIX_CALORIES).get());
        Carbs carbs = ParserUtil.parseCarbs(argMultimap.getValue(PREFIX_CARBS).get());
        Fats fats = ParserUtil.parseFats(argMultimap.getValue(PREFIX_FATS).get());
        Protein protein = ParserUtil.parseProtein(argMultimap.getValue(PREFIX_PROTEIN).get());

        Recipe recipe = new Recipe(name, ingredientList, calories, carbs, fats, protein);

        return new AddRecipeCommand(recipe);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
